package com.example.hippolyte.pools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by etienne on 21/11/2017.
 */

public class PoolsRepo {
    private DBHelper dbHelper;

    public PoolsRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Pool pool) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(Pool.KEY_id, pool.id);
        values.put(Pool.KEY_libelle,pool.libelle);
        values.put(Pool.KEY_ville, pool.ville);
        values.put(Pool.KEY_adresse, pool.url);
        values.put(Pool.KEY_codepostal, pool.codepostal);
        values.put(Pool.KEY_pointgeoX, pool.point_geoX);
        values.put(Pool.KEY_pointgeoY, pool.point_geoY);
        values.put(Pool.KEY_municipale, pool.municipale);

        // Inserting Row
        long pool_id = db.insert(Pool.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) pool_id;
    }

    public ArrayList<HashMap<String, String>> getPoolsList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Pool.KEY_id + "," +
                Pool.KEY_libelle + "," +
                Pool.KEY_ville + "," +
                Pool.KEY_adresse + "," +
                Pool.KEY_codepostal + "," +
                Pool.KEY_pointgeoX + "," +
                Pool.KEY_pointgeoY + "," +
                Pool.KEY_municipale +
                " FROM " + Pool.TABLE;

        //Pool pool = new Pool();
        ArrayList<HashMap<String, String>> poolList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pool = new HashMap<String, String>();
                pool.put("id", cursor.getString(cursor.getColumnIndex(Pool.KEY_id)));
                pool.put("libelle", cursor.getString(cursor.getColumnIndex(Pool.KEY_libelle)));
                pool.put("ville", cursor.getString(cursor.getColumnIndex(Pool.KEY_ville)));
                pool.put("adresse", cursor.getString(cursor.getColumnIndex(Pool.KEY_adresse)));
                pool.put("codepostal", cursor.getString(cursor.getColumnIndex(Pool.KEY_codepostal)));
                pool.put("pointgeoX", cursor.getString(cursor.getColumnIndex(Pool.KEY_pointgeoX)));
                pool.put("pointgeoY", cursor.getString(cursor.getColumnIndex(Pool.KEY_pointgeoY)));
                pool.put("municipale", cursor.getString(cursor.getColumnIndex(Pool.KEY_municipale)));
                poolList.add(pool);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return poolList;

    }

    public Pool getPoolById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Pool.KEY_id + "," +
                Pool.KEY_libelle + "," +
                Pool.KEY_ville + "," +
                Pool.KEY_adresse + "," +
                Pool.KEY_codepostal + "," +
                Pool.KEY_pointgeoX + "," +
                Pool.KEY_pointgeoY + "," +
                Pool.KEY_municipale +
                " FROM " + Pool.TABLE
                + " WHERE " +
                Pool.KEY_id + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Pool pool = new Pool();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                pool.id =cursor.getInt(cursor.getColumnIndex(Pool.KEY_id));
                pool.libelle =cursor.getString(cursor.getColumnIndex(Pool.KEY_libelle));
                pool.ville =cursor.getString(cursor.getColumnIndex(Pool.KEY_ville));
                pool.url =cursor.getString(cursor.getColumnIndex(Pool.KEY_adresse));
                pool.codepostal =cursor.getString(cursor.getColumnIndex(Pool.KEY_codepostal));
                pool.point_geoX =cursor.getString(cursor.getColumnIndex(Pool.KEY_pointgeoX));
                pool.point_geoY =cursor.getString(cursor.getColumnIndex(Pool.KEY_pointgeoY));
                pool.municipale =cursor.getString(cursor.getColumnIndex(Pool.KEY_municipale));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pool;
    }
}
