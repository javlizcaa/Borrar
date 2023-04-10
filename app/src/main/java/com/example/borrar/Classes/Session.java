package com.example.borrar.Classes;

public class Session {

    private int id;
    private int serie;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = String.valueOf(date);
    }
}
