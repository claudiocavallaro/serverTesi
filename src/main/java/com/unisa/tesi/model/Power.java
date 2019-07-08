package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "power")
public class Power {

    @Id
    private long timeStamp;


    private int voltage;
    private int power;
    private double current;


    public Power(int voltage, double current, int power){
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.timeStamp = System.currentTimeMillis();
    }

    @JsonCreator
    public Power(){}

    /*public String toString(){
        return " ---VOLTAGE : " + voltage + " ---CURRENT : " + current + " ---POWER : " + power;
    }*/

    public String toString(){
        return  this.voltage + "," + this.current + "," + this.power ;
    }

}
