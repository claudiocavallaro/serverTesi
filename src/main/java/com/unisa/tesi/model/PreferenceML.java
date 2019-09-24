package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PreferenceML {

    @Id
    private long timestamp;

    private int power;

    private String preference;

    private Traccia traccia;

    public float getNumber(String car){
        float myNumber = 0;
        switch (car){
            case "danceability":
                myNumber = traccia.getDanceability();
                break;
            case "energy":
                myNumber = traccia.getEnergy();
                break;
            case "loudness":
                myNumber = traccia.getLoudness();
                break;
            case "speechiness":
                myNumber = traccia.getSpeechiness();
                break;
            case "valence":
                myNumber = traccia.getValence();
                break;
            case "tempo":
                myNumber = traccia.getTempo();
                break;
        }
        return myNumber;
    }

    @JsonCreator
    public PreferenceML(){}

    public String toString(){
        return traccia.toString() + "\npreference : " + preference + "\npower : " + power;
    }
}
