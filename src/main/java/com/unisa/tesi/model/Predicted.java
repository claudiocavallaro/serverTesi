package com.unisa.tesi.model;

public class Predicted {

    private float danceability;
    private float valence;

    public Predicted(float danceability, float valence){
        this.danceability = danceability;
        this.valence = valence;
    }

    public Predicted(){}

    public float getDanceability() {
        return danceability;
    }

    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    public float getValence() {
        return valence;
    }

    public void setValence(float valence) {
        this.valence = valence;
    }

    public String toString(){
        return "Danceability: " + danceability + " Valence: " + valence ;
    }
}
