package com.example.hippolyte.pools;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PoolDetails extends AppCompatActivity {
    private TextView tvNom;
    private TextView tvAdresse;
    private TextView tvVille;
    private TextView tvCP;
    private TextView tvPtX;
    private TextView tvPtY;
    private Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_details);

        tvNom= (TextView)findViewById(R.id.nomPiscine);
        tvAdresse= (TextView)findViewById(R.id.urlVille);
        tvVille= (TextView)findViewById(R.id.nomVille);
        tvCP = (TextView) findViewById(R.id.cPVille);
        tvPtX = (TextView) findViewById(R.id.ptXVille);
        tvPtY = (TextView) findViewById(R.id.ptYVille);
        btnMap= (Button) findViewById(R.id.buttonMap);

        //recupere les infos de la piscine dans l'intent pour l'affichage des details
        Bundle bundle = getIntent().getExtras();
        tvNom.setText(bundle.getString("libelle"));
        tvVille.setText("Ville: "+bundle.getString("ville"));


        //souligne l'url
        SpannableString content = new SpannableString(bundle.getString("url"));
        content.setSpan(new UnderlineSpan(), 0, bundle.getString("url").length(), 0);
        tvAdresse.setText(content);

        tvCP.setText("Code postal: "+bundle.getString("cp"));
        tvPtX.setText(bundle.getString("ptX"));
        tvPtY.setText(bundle.getString("ptY"));


        //permet de rechercher l'url dans le navigateur
        tvAdresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link;
                link = tvAdresse.getText().toString();
                if (!(link.startsWith("http://") || link.startsWith("https://"))){
                    link="http://"+link;
                }
                Uri WebUrl = Uri.parse(link);
                Intent lancerNavigateur = new Intent(Intent.ACTION_VIEW);
                lancerNavigateur.setData(WebUrl);
                startActivity(lancerNavigateur);
            }
        });

        //permet de lancer la map
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ptx;
                String Pty;
                String nom;
                Ptx = tvPtX.getText().toString();
                Pty = tvPtY.getText().toString();
                nom = tvNom.getText().toString();

                Intent lancerMap = new Intent(getApplicationContext(),MapsActivity.class);
                lancerMap.putExtra("Ptx", Ptx);
                lancerMap.putExtra("Pty", Pty);
                lancerMap.putExtra("nom", nom);
                startActivity(lancerMap);

            }
        });
    }
}
