package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "phone")
public class PhonePreference {


    /*
    *       Devo provare ad inserire l'ultima misurazione di potenza fatta
    * */


    // POTENZA

    private Power power;

    private String idSong;
    private String preference;

    @Id
    private long timeStamp;


    public PhonePreference(String idSong, String preference, Power power){
        this.idSong = idSong;
        this.preference = preference;
        this.power = power;
        this.timeStamp = System.currentTimeMillis();
    }

    @JsonCreator
    public PhonePreference(){}


    public String toString(){
        return "PhonePref --- ID : " + idSong + " Preference : " + preference + "\n";
    }
}
