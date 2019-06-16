package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.unisa.tesi.api.TracceComponent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Preference {

    @Id
    private long timestamp;

    private User user;
    private Traccia traccia;

    private int preferenza;

    private Camera camera;
    private Power power;

    public Preference(User user, Traccia traccia, int preferenza){
        this.user = user;
        this.traccia = traccia;
        this.preferenza = preferenza;
        this.timestamp = System.currentTimeMillis();
    }

    @JsonCreator
    public Preference(){}

    public String toString(){

        return traccia.getName() + " ---- DA : " + user.getUid() + " ----- VALORE : " + preferenza + " ---- POTENZA : " + power.getPower() + " ----- CAMERA : " + camera.getNumber();
    }
}
