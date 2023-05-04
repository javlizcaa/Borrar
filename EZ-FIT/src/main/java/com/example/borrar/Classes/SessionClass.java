package com.example.borrar.Classes;

public class SessionClass {

    private int id;
    private int serie;
    private String date;
    private int userID;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public int getUserID() {return userID;}

    public void setUserID(int userID) {this.userID = userID;}
}
