package com.example.hippolyte.pools;

/**
 * Created by thomas on 16/11/2017.
 */

public class Pool {
    private int id;
    private String libelle;
    private String ville;
    private String adresse;
    private int codepostal;
    private double point_geoX;
    private double point_geoY;
    private boolean municipale;

    public Pool(int id,String libelle, String ville, String adresse, int codepostal, double point_geoX, double point_geoY, boolean municipale) {
        this.id=id;
        this.ville=ville;
        this.adresse=adresse;
        this.codepostal=codepostal;
        this.point_geoX=point_geoX;
        this.point_geoY=point_geoY;
        this.municipale=municipale;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getVille() {
        return ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getCodepostal() {
        return codepostal;
    }

    public double getPoint_geoX() {
        return point_geoX;
    }

    public double getPoint_geoY() {
        return point_geoY;
    }

    public boolean isMunicipale() {
        return municipale;
    }

}
