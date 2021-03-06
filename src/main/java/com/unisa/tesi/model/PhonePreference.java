package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "phone")
public class PhonePreference {

    // POTENZA

    private Power power;

    private String idSong;
    private String preference;

    @Id
    private long timeStamp;

    public PhonePreference(String idSong, String preference){
        this.idSong = idSong;
        this.preference = preference;
        this.timeStamp = System.currentTimeMillis();
    }

    @JsonCreator
    public PhonePreference(){}


    public String toString(){
        return "PhonePref --- ID : " + idSong + " Preference : " + preference + "\n";
    }
}
