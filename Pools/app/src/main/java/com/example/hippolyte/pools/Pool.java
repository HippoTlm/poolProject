package com.example.hippolyte.pools;

/**
 * Created by thomas on 16/11/2017.
 */

public class Pool {
    public int id;
    public String libelle;
    public String ville;
    public String adresse;
    public int codepostal;
    public double point_geoX;
    public double point_geoY;
    public boolean municipale;

    public static final String TABLE = "Pool";

    public static final String KEY_id = "id";
    public static final String KEY_libelle = "libelle";
    public static final String KEY_ville = "ville";
    public static final String KEY_adresse = "adresse";
    public static final String KEY_codepostal = "codepostal";
    public static final String KEY_pointgeoX = "point geo X";
    public static final String KEY_pointgeoY = "point geo Y";
    public static final String KEY_municipale = "municipale";
}
