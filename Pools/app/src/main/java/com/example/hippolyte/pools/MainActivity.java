package com.example.hippolyte.pools;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class MainActivity extends AppCompatActivity {

    private ListView listViewPools ;

    private PoolsRepo poolsRepo = new PoolsRepo(this);

    private static String url = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=mel_piscines&facet=commune&rows=-1";
    private ArrayList<HashMap<String,String>> poolsList;
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
        listViewPools =(ListView)findViewById(R.id.listViewPools);

        ArrayList<HashMap<String,String>> poolList = poolsRepo.getPoolsList();
        PoolAdaptater adapter = new PoolAdaptater(this,poolList);
        listViewPools.setAdapter(adapter);

        /*if(poolsList.size()!=0) {
            listViewPools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ville = (TextView) view.findViewById(R.id.ville);
                    libelle = (TextView) view.findViewById(R.id.nom);
                    String libelle2 = libelle.getText().toString();
                    String ville2=ville.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    intent.putExtra("libelle",libelle2);
                    intent.putExtra("ville",ville2);
                    startActivity(intent);
                }
            });


        }else{
            Toast.makeText(this,"No student!",Toast.LENGTH_SHORT).show();
        }*/
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


    class RecupererJson extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this );

            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(getApplicationContext(), "Récupération réussie !", Toast.LENGTH_SHORT).show();
                            poolsList = new ArrayList<HashMap<String, String>>();
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

                                    Pool newPool = new Pool();
                                    //newPool.id = id;
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

                                ArrayList<HashMap<String,String>> poolList = poolsRepo.getPoolsList();
                                PoolAdaptater adapter = new PoolAdaptater(MainActivity.this,poolList);
                                listViewPools.setAdapter(adapter);
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

}
