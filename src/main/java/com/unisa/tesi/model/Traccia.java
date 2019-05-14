package com.unisa.tesi.model;

import lombok.Data;

@Data
public class Traccia {

    private String name;
    private String artist;
    private String id;

    private String duration;
    private String loudness;
    private String speechiness;
    private String danceability;

    private String url;


    public Traccia(){}

}
