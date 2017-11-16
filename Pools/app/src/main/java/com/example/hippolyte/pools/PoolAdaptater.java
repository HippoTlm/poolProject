package com.example.hippolyte.pools;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thomas on 16/11/2017.
 */

public class PoolAdaptater extends ArrayAdapter<Pool>{


    public PoolAdaptater(Context context, List<Pool> pools) {
        super(context,0, pools);
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
            convertView.setTag(viewHolder);
        }

        Pool pool = getItem(position);
        viewHolder.nom.setText(pool.getLibelle());
        viewHolder.ville.setText(pool.getVille());
        viewHolder.image.setImageDrawable(new ColorDrawable(pool.getColor()));

        return convertView;
    }

    private class PoolViewHolder{
        public TextView nom ;
        public TextView ville;
        public ImageView image;
    }
}
