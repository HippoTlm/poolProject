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
import android.widget.ArrayAdapter;
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
    private TextView mTextView;
    private ListView listViewPools ;
    DBHelper myBDHelper;
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

        /*myBDHelper = new DBHelper(this);
        final ArrayList<Pool> ListeDesPools = myBDHelper.getAllPools();
        PoolAdaptater adaptater = new PoolAdaptater(MainActivity.this, ListeDesPools);
        listViewPools.setAdapter(adaptater);*/

        listViewPools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("pool",listViewPools.getItemAtPosition(position).toString());
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
                            //JSONArray listPools = response.getJSONArray(POOLS_LIBELLE);
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

        private void loadIntoListView(String json) throws JSONException {
            //creating a json array from the json string
            JSONArray jsonArray = new JSONArray(json);

            //creating a string array for listview
            String[] heroes = new String[jsonArray.length()];

            //looping through all the elements in json array
            for (int i = 0; i < jsonArray.length(); i++) {

                //getting json object from the json array
                JSONObject obj = jsonArray.getJSONObject(i);

                //getting the name from the json object and putting it inside string array
                heroes[i] = obj.getString("name");
            }

            //the array adapter to load data into list
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);

            //attaching adapter to listview
            //listViewPools.setAdapter(arrayAdapter);
        }

    }
}
