package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "read")
public class Read {

    @Id
    private long timestamp;

    private String uid;


    @JsonCreator
    public Read(){}

    public Read(String uid){
        this.uid = uid;
        this.timestamp = System.currentTimeMillis();
    }




}