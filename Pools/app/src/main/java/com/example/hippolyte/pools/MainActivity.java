package com.example.hippolyte.pools;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPools ;
    private String [] pools=new String[]{
       "Piscine 1","Piscine 2","Piscine3", "Piscine 4","Piscine 5","Piscine 6"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewPools =(ListView)findViewById(R.id.listViewPools);

        List<Pool> pools=genererPools();

        PoolAdaptater adaptater = new PoolAdaptater(MainActivity.this, pools);
        listViewPools.setAdapter(adaptater);

        listViewPools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("pool",listViewPools.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });




    }


    private List<Pool> genererPools(){
        List<Pool> pools=new ArrayList<>();
        pools.add(new Pool(Color.GREEN,"Nom 1","Adresse 1","Ville 1",59001,1));
        pools.add(new Pool(Color.GREEN,"Nom 2","Adresse 2","Ville 2",59002,2));
        pools.add(new Pool(Color.GREEN,"Nom 3","Adresse 3","Ville 3",59003,3));
        pools.add(new Pool(Color.GREEN,"Nom 4","Adresse 4","Ville 4",59004,4));
        pools.add(new Pool(Color.GREEN,"Nom 5","Adresse 5","Ville 5",59005,5));
        pools.add(new Pool(Color.GREEN,"Nom 6","Adresse 6","Ville 6",59006,6));
        return pools;
    }



}
