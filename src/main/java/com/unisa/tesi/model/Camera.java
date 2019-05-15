package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "camera")
public class Camera {

    @Id
    private long timeStamp;

    private int number;
    private String area;

    @JsonCreator
    public Camera(){}

    public Camera(String area, int number){
        this.area = area;
        this.number = number;
        this.timeStamp = System.currentTimeMillis();
    }
}
