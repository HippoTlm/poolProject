package com.example.hippolyte.pools;

/**
 * Created by thomas on 16/11/2017.
 */

public class Pool {
    private String libelle;
    private String adresse;
    private String ville;
    private int codepostale ;
    private int point_geo;
    private int color;

    public Pool(int color ,String libelle, String adresse,String ville,int codepostale,int point_geo) {
        this.libelle=libelle;
        this.adresse=adresse;
        this.codepostale=codepostale;
        this.ville=ville;
        this.point_geo=point_geo;
        this.color=color;

    }

    public int getCodepostale() {
        return codepostale;
    }

    public int getPoint_geo() {
        return point_geo;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getVille() {
        return ville;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCodepostale(int codepostale) {
        this.codepostale = codepostale;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setPoint_geo(int point_geo) {
        this.point_geo = point_geo;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
