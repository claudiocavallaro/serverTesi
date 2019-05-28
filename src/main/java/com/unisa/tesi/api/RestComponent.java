package com.unisa.tesi.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.unisa.tesi.persistence.*;
import com.unisa.tesi.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestComponent {

    private UserRepo userRepo;
    private CameraRepo cameraRepo;
    private PowerRepo powerRepo;
    private TracciaRepo tracciaRepo;
    private PreferenceRepo preferenceRepo;

    public RestComponent(UserRepo userRepo, CameraRepo cameraRepo, PowerRepo powerRepo, TracciaRepo tracciaRepo, PreferenceRepo preferenceRepo){
        this.userRepo = userRepo;
        this.cameraRepo = cameraRepo;
        this.powerRepo = powerRepo;
        this.tracciaRepo = tracciaRepo;
        this.preferenceRepo = preferenceRepo;
    }

    // ------------ DB TOTAL LIST ----------------------
    @RequestMapping(value = "/total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String get(){
        Gson gson = new GsonBuilder().create();
        List<User> user = userRepo.findAll();
        String result = gson.toJson(user);
        return result;
    }

    @GetMapping("/total/preference")
    @ResponseBody
    public String getPreference(){
        Gson gson = new GsonBuilder().create();

        List<Preference> listaPreferenze = preferenceRepo.findAll();

        return gson.toJson(listaPreferenze);
    }
    //-------------------------------------------------
    //--------- PREFERENCE ----------------------------
    @GetMapping("/api/userpreference")
    @ResponseBody
    public String getUserPreference(@RequestParam String uid){
        Gson gson = new GsonBuilder().create();

        System.out.println(uid);
        List<Preference> listaPreferenzeUser = preferenceRepo.findByUserId(uid);

        System.out.println(listaPreferenzeUser.size());
        return gson.toJson(listaPreferenzeUser);
    }

    @GetMapping("/api/preference")
    @ResponseBody
    public String setPreference(@RequestParam String uid, int preferenza){
        Gson gson = new GsonBuilder().create();
        User user = findByUid(uid);

        Preference p = null;

        if (user != null && user.isInside()){
            Traccia t = TracceComponent.getTraccia();

            p = new Preference(user, t, preferenza);

            preferenceRepo.save(p);
        }

        if (p != null){
            return gson.toJson(p);
        } else {
            return null;
        }
    }

    //---- SET ENTRANCE ---------
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

        //**** UPDATE ON DB
        userRepo.save(user);
        //-----------------

        Gson gson = new GsonBuilder().create();
        result = gson.toJson(user);

        return result;
    }

    // -------- SENSOR DATA ---------
    @GetMapping("/api/power")
    @ResponseBody
    public String getPower(@RequestParam String voltage, String current, String power){
        Gson gson = new GsonBuilder().create();
        System.out.println("Voltage " + voltage + " current " + current + " power " + power);
        Power powerObj = new Power(Integer.valueOf(voltage), Double.valueOf(current), Integer.valueOf(power));

        //SAVE ON DB
        this.powerRepo.save(powerObj);

        return gson.toJson(powerObj);
    }


    @GetMapping("/api/camera")
    @ResponseBody
    public String fromCamera(@RequestParam String area, String number){
        Gson gson = new GsonBuilder().create();
        System.out.println("---AREA : " + area + " ----NUMBER : " + number);
        Camera camera = new Camera(area, Integer.valueOf(number));

        //SAVE ON DB
        this.cameraRepo.save(camera);

        return gson.toJson(camera);
    }

    //---------------------------------------------------------

    private User findByUid(String uid) {
        for(User user : userRepo.findAll()){
            if (user.getUid().equals(uid)){
                return user;
            }
        }
        return null;
    }

    @GetMapping("/api/nexttrack")
    @ResponseBody
    public void nextTrack(){
        TracceComponent.setSleep(true);
    }


}
