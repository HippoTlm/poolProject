package com.example.hippolyte.pools;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "pool.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_POOL = "CREATE TABLE " + Pool.TABLE  + "("
                + Pool.KEY_id  + " INTEGER PRIMARY KEY,"
                + Pool.KEY_libelle + " TEXT, "
                + Pool.KEY_ville + " TEXT, "
                + Pool.KEY_adresse + " TEXT, "
                + Pool.KEY_codepostal + " INTEGER, "
                + Pool.KEY_pointgeoX + " DOUBLE, "
                + Pool.KEY_pointgeoY + " DOUBLE, "
                + Pool.KEY_municipale + " BOOLEAN )";

        db.execSQL(CREATE_TABLE_POOL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Pool.TABLE);

        // Create tables again
        onCreate(db);

    }
}
