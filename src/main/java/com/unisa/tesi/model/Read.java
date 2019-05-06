package com.unisa.tesi.model;

import lombok.Data;

@Data
public class Read {

    private long timestamp;
    private String uid;

    private int state;


    public Read(String uid){
        this.uid = uid;
        this.timestamp = System.currentTimeMillis();
    }




}