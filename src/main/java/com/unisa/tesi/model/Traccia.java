package com.unisa.tesi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tracce")
public class Traccia {

    @Id
    private String _id;

    private String name;
    private String artist;

    private String duration;
    private String loudness;
    private String speechiness;
    private String danceability;

    private String url;


    public Traccia(){}

}
