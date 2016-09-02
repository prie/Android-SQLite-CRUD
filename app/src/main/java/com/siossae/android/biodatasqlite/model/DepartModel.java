package com.siossae.android.biodatasqlite.model;

/**
 * Created by pri on 23/08/16.
 */
public class DepartModel {
    private long id, idKepala;
    private String depart;
    private String nmKepala;

    public DepartModel() {
    }

    public DepartModel(long id, long idKepala, String depart, String nmKepala) {
        this.id = id;
        this.idKepala = idKepala;
        this.depart = depart;
        this.nmKepala = nmKepala;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdKepala() {
        return idKepala;
    }

    public void setIdKepala(long idKepala) {
        this.idKepala = idKepala;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getNmKepala() {
        return nmKepala;
    }

    public void setNmKepala(String nmKepala) {
        this.nmKepala = nmKepala;
    }
}
