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

    public String toString(){
        return "--- PLAYING :" + name + " --- " + artist + " ---- " + this.energy;
    }

}
