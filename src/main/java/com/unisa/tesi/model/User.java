package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "listu")
public class User {

    private String name;
    private String bornDate;
    private int ingressi;
    private boolean inside;


    @Id
    private String uid;

    @JsonCreator
    public User(){}


    public User(String name, String bornDate, int ingressi, String uid){
        this.name = name;
        this.bornDate = bornDate;
        this.ingressi = ingressi;
        this.uid = uid;
        this.inside = false;
    }


}
