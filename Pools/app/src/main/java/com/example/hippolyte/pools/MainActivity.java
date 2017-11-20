package com.example.hippolyte.pools;

import android.content.Intent;
import android.graphics.Color;

import android.os.AsyncTask;
import android.support.v4.util.Pools;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView libelle ;
    TextView ville ;


    private TextView mTextView;
    private ListView listViewPools ;
    private String [] pools=new String[]{
       "Piscine 1","Piscine 2","Piscine3", "Piscine 4","Piscine 5","Piscine 6"
    };
    public static final String POOLS_LIBELLE = "libelle";
    public static final String POOLS_CITY = "ville";
    public static final String POOLS_ADRESSE = "adresse";
    public static final String POOLS_CODE_POSTAL = "code postal";
    public static final String POOLS_POINT_GEOX = "point geo X";
    public static final String POOLS_POINT_GEOY = "point geo Y";
    public static final String POOLS_MUNICIPALE = "municipale";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewPools =(ListView)findViewById(R.id.listViewPools);
        mTextView = (TextView) findViewById(R.id.testJSON);

        List<Pool> pools=genererPools();

        PoolAdaptater adaptater = new PoolAdaptater(MainActivity.this, pools);
        listViewPools.setAdapter(adaptater);

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

            String url ="https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=mel_piscines&facet=commune";

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //ligne d'en dessous juste pour test, A SUPPRIMER
                            mTextView.setText("Response is: "+ response.toString());
                            try {
                                JSONArray listPools = response.getJSONArray(POOLS_LIBELLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), "Récupération réussie !", Toast.LENGTH_SHORT).show();
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


    private List<Pool> genererPools(){
        List<Pool> pools=new ArrayList<>();
        pools.add(new Pool(Color.GREEN,"Nom 1","Adresse 1","Ville 1",59001,1,3.2,true));
        pools.add(new Pool(Color.GREEN,"Nom 2","Adresse 2","Ville 2",59002,2,3.2,true));
        pools.add(new Pool(Color.GREEN,"Nom 3","Adresse 3","Ville 3",59003,3,3.2,true));
        pools.add(new Pool(Color.GREEN,"Nom 4","Adresse 4","Ville 4",59004,4,3.2,true));
        pools.add(new Pool(Color.GREEN,"Nom 5","Adresse 5","Ville 5",59005,5,3.2,true));
        pools.add(new Pool(Color.GREEN,"Nom 6","Adresse 6","Ville 6",59006,6,3.2,true));
        return pools;
    }



}
