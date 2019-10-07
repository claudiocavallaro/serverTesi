package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tracce")
public class Traccia {

    @Id
    private String id;

    private String name;
    private String artist;

    private int duration;
    private float energy;
    private float loudness;
    private float speechiness;
    private float danceability;
    private float tempo;
    private float valence;

    private String url;

    @JsonCreator
    public Traccia(){}

    public float getNumber(String car){
        float myNumber = 0;
        switch (car){
            case "danceability":
                myNumber = this.getDanceability();
                break;
            case "energy":
                myNumber = this.getEnergy();
                break;
            case "loudness":
                myNumber = this.getLoudness();
                break;
            case "speechiness":
                myNumber = this.getSpeechiness();
                break;
            case "valence":
                myNumber = this.getValence();
                break;
            case "tempo":
                myNumber = this.getTempo();
                break;
        }
        return myNumber;
    }

    public String toString(){
        return  name + " --- " + artist + "\n";
    }

}
