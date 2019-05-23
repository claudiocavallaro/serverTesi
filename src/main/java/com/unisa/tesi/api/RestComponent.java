package com.unisa.tesi.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.unisa.tesi.persistence.*;
import com.unisa.tesi.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestComponent {

    private UserRepo userRepo;
    private CameraRepo cameraRepo;
    private PowerRepo powerRepo;
    private TracciaRepo tracciaRepo;

    public RestComponent(UserRepo userRepo, CameraRepo cameraRepo, PowerRepo powerRepo, TracciaRepo tracciaRepo){
        this.userRepo = userRepo;
        this.cameraRepo = cameraRepo;
        this.powerRepo = powerRepo;
        this.tracciaRepo = tracciaRepo;
    }


    @RequestMapping(value = "/total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String get(){
        Gson gson = new GsonBuilder().create();
        List<User> user = userRepo.findAll();
        String result = gson.toJson(user);
        return result;
    }

    @GetMapping("/api/preference")
    @ResponseBody
    public void setPreference(@RequestParam String uid, String idTraccia, int preferenza){

        
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

        //**** UPDATE ON DB
        userRepo.save(user);
        //-----------------

        Gson gson = new GsonBuilder().create();
        result = gson.toJson(user);

        return result;
    }


    @GetMapping("/api/power")
    @ResponseBody
    public String getPower(@RequestParam String voltage, String current, String power){
        System.out.println("Voltage " + voltage + " current " + current + " power " + power);
        Power powerObj = new Power(Integer.valueOf(voltage), Double.valueOf(current), Integer.valueOf(power));

        //SAVE ON DB
        this.powerRepo.save(powerObj);

        return powerObj.toString();
    }


    @GetMapping("/api/camera")
    @ResponseBody
    public String fromCamera(@RequestParam String area, String number){
        System.out.println("---AREA : " + area + " ----NUMBER : " + number);
        Camera camera = new Camera(area, Integer.valueOf(number));

        //SAVE ON DB
        this.cameraRepo.save(camera);

        return camera.toString();
    }


    private User findByUid(String uid) {
        for(User user : userRepo.findAll()){
            if (user.getUid().equals(uid)){
                return user;
            }
        }
        return null;
    }


}
