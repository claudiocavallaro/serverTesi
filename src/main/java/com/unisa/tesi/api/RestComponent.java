package com.unisa.tesi.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.Unirest;
import com.unisa.tesi.Application;

import com.unisa.tesi.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RestComponent {

    private Gym gym = new Gym("gymnasium");

    @RequestMapping(value = "/total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String get(){
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson(gym.getTotalList());
        return result;
    }

    @GetMapping("/api/entrance")
    @ResponseBody
    public String getUID(@RequestParam String uid){
        System.out.println(uid);

        User user = findByUid(uid);
        String result = "";


        boolean flag = false;

        if(user != null){

            // questo serve per capire se esiste una persona con quella scheda
            if(user.getUid().equals(uid)){
                // se è all'interno faccio in modo che esca altrimenti diminuisco gli ingressi
                if(user.isInside() == true){
                    user.setInside(false);
                } else {
                    /* Questa cosa degli ingressi può farla anche il raspberry
                       così appoggia il tag, chiede riscontro dell'utente
                       apre il tornello invia per inside true
                    * */
                    if(user.getIngressi() >= 1){
                        user.setIngressi(user.getIngressi() - 1);
                        user.setInside(true);
                        flag = true;
                    } else {
                        return "Non ci sono ingressi disponibili";
                    }
                }
            }
        } else {
            return "Nothing";
        }


        Gson gson = new GsonBuilder().create();
        result = gson.toJson(user);

        return result;
    }


    @GetMapping("/api/power")
    @ResponseBody
    public String getPower(@RequestParam String voltage, String current, String power){
        System.out.println("Voltage " + voltage + " current " + current + " power " + power);
        Power powerObj = new Power(Integer.valueOf(voltage), Double.valueOf(current), Integer.valueOf(power));
        return powerObj.toString();
    }


    @GetMapping("/api/camera")
    @ResponseBody
    public String fromCamera(@RequestParam String area, int value){
        System.out.println("---AREA : " + area + " ----NUMBER : " + value);
        Camera camera = new Camera(area, value);
        return camera.toString();
    }


    private User findByUid(String uid) {
        ArrayList<User> lista = gym.getTotalList();
        for(User user : lista){
            if (user.getUid().equals(uid)){
                return user;
            }
        }
        return null;
    }


}
