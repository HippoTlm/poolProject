package com.example.hippolyte.pools;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomas on 16/11/2017.
 */

public class PoolAdapter extends ArrayAdapter<HashMap<String,String>>{

    public PoolAdapter(Activity context, ArrayList mapArrayList) {
        super(context, 0, mapArrayList);
    }
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_pool,parent, false);
        }

        PoolViewHolder viewHolder = (PoolViewHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new PoolViewHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
            viewHolder.ville = (TextView) convertView.findViewById(R.id.ville);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.cp = (TextView) convertView.findViewById(R.id.textViewCP);
            viewHolder.url = (TextView) convertView.findViewById(R.id.textViewURL);
            viewHolder.ptX = (TextView) convertView.findViewById(R.id.textViewPTX);
            viewHolder.ptY = (TextView) convertView.findViewById(R.id.textViewPTY);
            convertView.setTag(viewHolder);
        }

        //recupere les parametres d'une piscine pour l'affichage dans la liste
        HashMap<String,String> pool = getItem(position);
        viewHolder.nom.setText(pool.get("libelle"));
        viewHolder.ville.setText(pool.get("ville"));
        viewHolder.url.setText(pool.get("adresse"));
        viewHolder.cp.setText(pool.get("codepostal"));
        viewHolder.ptX.setText(pool.get("pointgeoX"));
        viewHolder.ptY.setText(pool.get("pointgeoY"));

        if (pool.get("municipale").equals("true")){
            viewHolder.image.setImageDrawable(new ColorDrawable(Color.GREEN));
        }else if (pool.get("municipale").equals("false")){
            viewHolder.image.setImageDrawable(new ColorDrawable(Color.RED));
        }else{
            viewHolder.image.setImageDrawable(new ColorDrawable(Color.YELLOW));
        }

        return convertView;
    }

    private class PoolViewHolder{
        public TextView nom ;
        public TextView ville;
        public ImageView image;
        public TextView cp;
        public TextView url;
        public TextView ptX;
        public TextView ptY;
    }
}
