package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class Camera {

    private int number;
    private String area;

    @JsonCreator
    public Camera(){}

    public Camera(String area, int number){
        this.area = area;
        this.number = number;
    }
}
