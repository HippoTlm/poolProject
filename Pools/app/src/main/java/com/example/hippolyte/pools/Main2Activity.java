package com.example.hippolyte.pools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView nom;
    TextView addresse;
    TextView ville;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nom= (TextView)findViewById(R.id.nom);
        addresse= (TextView)findViewById(R.id.adresse);
        ville= (TextView)findViewById(R.id.ville);

        Bundle bundle = getIntent().getExtras();

        nom.setText(bundle.getString("pool"));



    }
}
