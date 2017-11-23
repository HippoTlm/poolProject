package com.example.hippolyte.pools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PoolDetails extends AppCompatActivity {
    private TextView tvNom;
    private TextView tvAdresse;
    private TextView tvVille;
    private TextView tvCP;
    private TextView tvPtX;
    private TextView tvPtY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvNom= (TextView)findViewById(R.id.nomPiscine);
        tvAdresse= (TextView)findViewById(R.id.urlVille);
        tvVille= (TextView)findViewById(R.id.nomVille);
        tvCP = (TextView) findViewById(R.id.cPVille);
        tvPtX = (TextView) findViewById(R.id.ptXVille);
        tvPtY = (TextView) findViewById(R.id.ptYVille);

        Bundle bundle = getIntent().getExtras();
        tvNom.setText(bundle.getString("libelle"));
        tvVille.setText(bundle.getString("ville"));
        tvAdresse.setText(bundle.getString("url"));
        tvCP.setText(bundle.getString("cp"));
        tvPtX.setText(bundle.getString("ptX"));
        tvPtY.setText(bundle.getString("ptY"));
        Toast.makeText(this, tvPtX.getText()+" -- "+tvPtY.getText(), Toast.LENGTH_SHORT).show();
    }
}
