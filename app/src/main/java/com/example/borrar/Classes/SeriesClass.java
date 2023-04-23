package com.example.borrar.Classes;

public class SeriesClass {

    private int id;
    private int exercise;
    private int repetitions;
    private String weight;
    private String rest;
    private String notes;
    private int visible;

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getExercise() {return exercise;}

    public void setExercise(int exercise) {
        this.exercise = exercise;
    }

    public String getWeight() {return weight;}

    public void setWeight(String weight) {this.weight = weight;}

    public String getRest() {return rest;}

    public void setRest(String rest) {this.rest = rest;}

    public String getNotes() {return notes;}

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setNotes(String notes) {this.notes = notes;}
}
