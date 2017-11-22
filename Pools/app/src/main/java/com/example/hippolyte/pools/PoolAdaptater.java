package com.example.hippolyte.pools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 16/11/2017.
 */

public class PoolAdaptater extends ArrayAdapter<String>{

    private final Activity context;
    private final String itemName;
    private final String itemDescription;

    public PoolAdaptater(Activity context, String itemname, String itemdescription) {
        super(context, R.layout.row_pool);
        this.context=context;
        this.itemName =itemname;
        this.itemDescription = itemdescription ;

    }
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        /*if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_pool,parent, false);
        }

        PoolViewHolder viewHolder = (PoolViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PoolViewHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
            viewHolder.ville = (TextView) convertView.findViewById(R.id.ville);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }

        viewHolder.nom.setText(itemName);
        viewHolder.ville.setText(itemDescription);
        if (itemName.contains("MUNICIPALE")){
            viewHolder.image.setImageDrawable(new ColorDrawable(Color.GREEN));
        }else {
            viewHolder.image.setImageDrawable(new ColorDrawable(Color.RED));
        }*/
        PoolViewHolder viewHolder = (PoolViewHolder) convertView.getTag();

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.row_pool, null,true);

        TextView titreListe = (TextView) rowView.findViewById(R.id.nom);
        ImageView imageListe = (ImageView) rowView.findViewById(R.id.icon);
        TextView descriptionListe = (TextView) rowView.findViewById(R.id.ville);

        titreListe.setText(itemName);
        descriptionListe.setText(itemDescription);

            if (itemName.contains("MUNICIPALE")){
                viewHolder.image.setImageDrawable(new ColorDrawable(Color.GREEN));
            }else {
                viewHolder.image.setImageDrawable(new ColorDrawable(Color.RED));
            }

        //return convertView;
        return rowView;
    }

    private class PoolViewHolder{
        public TextView nom ;
        public TextView ville;
        public ImageView image;
    }
}
