package com.example.hippolyte.pools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDataBase.db";
    public static final String POOLS_TABLE_NAME = "pools";
    public static final String POOLS_COLUMN_ID = "id";
    public static final String POOLS_COLUMN_LIBELLE = "libelle";
    public static final String POOLS_COLUMN_CITY = "ville";
    public static final String POOLS_COLUMN_ADRESSE = "adresse";
    public static final String POOLS_COLUMN_CODE_POSTAL = "code postal";
    public static final String POOLS_COLUMN_POINT_GEOX = "point geo X";
    public static final String POOLS_COLUMN_POINT_GEOY = "point geo Y";
    public static final String POOLS_COLUMN_MUNICIPALE = "municipale";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        //addContactDemo() ;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " +
                        POOLS_TABLE_NAME + " ("+
                        POOLS_COLUMN_ID + " integer primary key AUTOINCREMENT, " +
                        POOLS_COLUMN_LIBELLE + " text, " +
                        POOLS_COLUMN_ID + " text, " +
                        POOLS_COLUMN_ADRESSE + " text, " +
                        POOLS_COLUMN_CODE_POSTAL + " text, " +
                        POOLS_COLUMN_POINT_GEOY + " text, " +
                        POOLS_COLUMN_POINT_GEOY + " text, " +
                        POOLS_COLUMN_MUNICIPALE + " text)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+POOLS_TABLE_NAME+" ");
        onCreate(db);
    }


    public void addPoolDemo() {
        insertPool("PISCINE MUNICIPALE DE LOMME", "LOMME", "", 59160, 3.01, 50.64, true);
    }



    public boolean insertPool (String libelle, String ville, String adresse, int codepostal, double point_geoX, double point_geoY, boolean municipale) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POOLS_COLUMN_LIBELLE, libelle);
        contentValues.put(POOLS_COLUMN_CITY, ville);
        contentValues.put(POOLS_COLUMN_ADRESSE, adresse);
        contentValues.put(POOLS_COLUMN_CODE_POSTAL, codepostal);
        contentValues.put(POOLS_COLUMN_POINT_GEOX, point_geoX);
        contentValues.put(POOLS_COLUMN_POINT_GEOY, point_geoY);
        contentValues.put(POOLS_COLUMN_MUNICIPALE, municipale);
        db.insert(POOLS_TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+POOLS_TABLE_NAME+" where "+POOLS_COLUMN_ID+"="+id, null );
        return res;
    }

    public ArrayList<Pool> getAllPools() {
        /*ArrayList<String> listePools = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+POOLS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            listePools.add(res.getString(res.getColumnIndex(POOLS_TABLE_NAME)));
            res.moveToNext();
        }
        return listePools;*/
        return null;
    }

}
