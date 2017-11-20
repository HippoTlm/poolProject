package com.example.hippolyte.pools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView nom2;
    TextView addresse;
    TextView ville2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nom2= (TextView)findViewById(R.id.nom1);
        addresse= (TextView)findViewById(R.id.adresse);
        ville2= (TextView)findViewById(R.id.ville1);

        Bundle bundle = getIntent().getExtras();

        nom2.setText(bundle.getString("libelle"));
        ville2.setText(bundle.getString("ville"));



    }
}
