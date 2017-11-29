package com.example.hippolyte.pools;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;

import static com.example.hippolyte.pools.R.id.ville;

public class MainActivity extends AppCompatActivity {

    private ListView tvListViewPools ;
    private TextView tvVille;
    private TextView tvLibelle;
    private TextView tvURL;
    private TextView tvCP;
    private TextView tvPointGeoX;
    private TextView tvPointGeoY;

    private PoolsRepo poolsRepo = new PoolsRepo(this);
    private DBHelper dbHelper = new DBHelper(this);
    private SQLiteDatabase db;

    private static String url =
            "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=mel_piscines&facet=commune&rows=-1";
    private String commune;
    private String codepostal;
    private String pointgeoX;
    private String pointgeoY;
    private String urlPool;
    private String libelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvListViewPools =(ListView)findViewById(R.id.listViewPools);

        //appel de l'adaptater pour afficher les piscines en liste
        ArrayList<HashMap<String,String>> poolList = poolsRepo.getPoolsList();
        PoolAdapter adapter = new PoolAdapter(this,poolList);
        tvListViewPools.setAdapter(adapter);

        if(poolList.size()!=0) {
            tvListViewPools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //clic sur une piscine pour afficher ses details
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvVille = (TextView) view.findViewById(ville);
                    tvLibelle = (TextView) view.findViewById(R.id.nom);
                    tvCP = (TextView) view.findViewById(R.id.textViewCP);
                    tvURL = (TextView) view.findViewById(R.id.textViewURL);
                    tvPointGeoX = (TextView) view.findViewById(R.id.textViewPTX);
                    tvPointGeoY = (TextView) view.findViewById(R.id.textViewPTY);

                    String libelle2 = tvLibelle.getText().toString();
                    String ville2 = tvVille.getText().toString();
                    String url2 = tvURL.getText().toString();
                    String cp2 = tvCP.getText().toString();
                    String ptX2 = tvPointGeoX.getText().toString();
                    String ptY2 = tvPointGeoY.getText().toString();

                    //envoi des infos de la piscine dans un intent
                    Intent intent = new Intent(getApplicationContext(),PoolDetails.class);
                    intent.putExtra("cp",cp2);
                    intent.putExtra("url",url2);
                    intent.putExtra("libelle",libelle2);
                    intent.putExtra("ville",ville2);
                    intent.putExtra("ptX",ptX2);
                    intent.putExtra("ptY",ptY2);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this,"No pools yet!",Toast.LENGTH_SHORT).show();
        }
        //permet d'afficher la liste des piscines
        afficherList();
    }


    @Override
    /**
     *crée le menu
     */
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m);
        return true;
    }


    /**
     * Permet de savoir quelle est la méthode associée à  l'élément du menu qui a été selectionné
     * @param item l'item du menu selectioné
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id== R.id.action_rotate) {
            RecupererJson recupererJson = new RecupererJson();
            recupererJson.execute();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //recuperation du JSON en arriere plan
    class RecupererJson extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this );

            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            deleteDatabase(dbHelper.getDatabaseName());
                            Toast.makeText(getApplicationContext(),
                                    "Récupération réussie !",
                                    Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this,
                                    "Les piscines municipales sont en bleu.",
                                    Toast.LENGTH_LONG).show();

                            //parsing du JSON
                            try{
                                JSONArray records = response.getJSONArray("records");
                                for (int i = 0; i < records.length(); i++){
                                    JSONObject piscine = records.getJSONObject(i);

                                    JSONObject pools = piscine.getJSONObject("fields");
                                    commune = pools.getString("commune");
                                    codepostal = pools.getString("code_postal");
                                    urlPool = pools.getString("url");
                                    libelle = pools.getString("libelle");

                                    JSONArray coordonnees = pools.getJSONArray("geo_point_2d");
                                    pointgeoY = String.valueOf(coordonnees.getDouble(0));
                                    pointgeoX = String.valueOf(coordonnees.getDouble(1));

                                    //implementation de la base de donnees avec chaque piscine de la liste
                                    Pool newPool = new Pool();
                                    newPool.url = urlPool;
                                    newPool.libelle = libelle;
                                    newPool.ville = commune;
                                    newPool.codepostal = codepostal;
                                    newPool.point_geoX = pointgeoX;
                                    newPool.point_geoY = pointgeoY;
                                    if (libelle.contains(" MUNICIPALE ") || libelle.contains(" MUNICIPAL ") ){
                                        newPool.municipale="true";
                                    }else{
                                        newPool.municipale="false";
                                    }
                                    poolsRepo.insert(newPool);
                                }
                                afficherList();

                            }catch (JSONException e){
                                Toast.makeText(MainActivity.this, "An error ocurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "La récupération a échouée", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsObjRequest);
            return null;
        }

    }

    public void afficherList(){
        //creation de la liste des piscines
        ArrayList<HashMap<String,String>> poolList = poolsRepo.getPoolsList();
        PoolAdapter adapter = new PoolAdapter(this,poolList);
        tvListViewPools.setAdapter(adapter);

        if(poolList.size()!=0) {
            tvListViewPools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //clic sur une piscine pour afficher ses details
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvVille = (TextView) view.findViewById(ville);
                    tvLibelle = (TextView) view.findViewById(R.id.nom);
                    tvCP = (TextView) view.findViewById(R.id.textViewCP);
                    tvURL = (TextView) view.findViewById(R.id.textViewURL);
                    tvPointGeoX = (TextView) view.findViewById(R.id.textViewPTX);
                    tvPointGeoY = (TextView) view.findViewById(R.id.textViewPTY);

                    String libelle2 = tvLibelle.getText().toString();
                    String ville2 = tvVille.getText().toString();
                    String url2 = tvURL.getText().toString();
                    String cp2 = tvCP.getText().toString();
                    String ptX2 = tvPointGeoX.getText().toString();
                    String ptY2 = tvPointGeoY.getText().toString();

                    //envoi des infos de la piscine dans un intent
                    Intent intent = new Intent(getApplicationContext(),PoolDetails.class);
                    intent.putExtra("cp",cp2);
                    intent.putExtra("url",url2);
                    intent.putExtra("libelle",libelle2);
                    intent.putExtra("ville",ville2);
                    intent.putExtra("ptX",ptX2);
                    intent.putExtra("ptY",ptY2);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this,"No pools yet!",Toast.LENGTH_SHORT).show();
        }
    }

}
