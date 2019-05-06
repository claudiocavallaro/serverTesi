package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.ArrayList;

@Data
public class User {

    private String name;
    private String bornDate;
    private int ingressi;
    private boolean inside;


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
