package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.ArrayList;

@Data
public class Gym {

    private String name;
    private ArrayList<User> totalList;

    public Gym(String name){
        this.name = name;
        totalList = new ArrayList<>();
        popolaLista();
    }

    private void popolaLista() {
        User u1 = new User("nome1", "data1", 10, "1cbafe2b");
        User u2 = new User("nome2", "data2", 20, "uid2");
        User u3 = new User("nome3", "data3", 30, "uid3");

        totalList.add(u1);
        totalList.add(u2);
        totalList.add(u3);
    }

    @JsonCreator
    public Gym(){}


    public String toString(){
        return "NOME : " + this.name + "\nUtenti : "+ this.totalList.size();
    }




}
