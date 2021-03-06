package com.unisa.tesi.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.unisa.tesi.persistence.*;
import com.unisa.tesi.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

@RestController
public class RestComponent {

    private UserRepo userRepo;
    private CameraRepo cameraRepo;
    private PowerRepo powerRepo;
    private TracciaRepo tracciaRepo;
    private PreferenceRepo preferenceRepo;
    private PhonePrefRepo phonePrefRepo;

    private TracceComponent tracceComponent;

    public RestComponent(UserRepo userRepo, CameraRepo cameraRepo, PowerRepo powerRepo,
                         TracciaRepo tracciaRepo, PreferenceRepo preferenceRepo,
                         TracceComponent tracceComponent, PhonePrefRepo phonePrefRepo){
        this.userRepo = userRepo;
        this.cameraRepo = cameraRepo;
        this.powerRepo = powerRepo;
        this.tracciaRepo = tracciaRepo;
        this.preferenceRepo = preferenceRepo;
        this.tracceComponent = tracceComponent;
        this.phonePrefRepo = phonePrefRepo;
    }

    //------------- API KEYS ---------------------------

    @GetMapping("api/cell/phone/cdsgrrte/xxsgh/keys")
    @ResponseBody
    public String getKey(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ApiKeys apiKeys = new ApiKeys();

        System.out.println("--- REQUEST KEY PHONE ---");
        return gson.toJson(apiKeys);
    }

    // ------------ DB TOTAL LIST ----------------------
    @RequestMapping(value = "/total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String get(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> user = userRepo.findAll();
        String result = gson.toJson(user);
        return result;
    }

    @GetMapping("/total/preference")
    @ResponseBody
    public String getPreference(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Preference> listaPreferenze = preferenceRepo.findAll();

        return gson.toJson(listaPreferenze);
    }

    @GetMapping("/total/power")
    @ResponseBody
    public String getPower(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Power> listaPower = powerRepo.findAll();

        for (Power p : listaPower){
            System.out.println(p.toString());
        }
        return gson.toJson(listaPower);
    }

    @GetMapping("/total/phonepref")
    @ResponseBody
    public String getTotalPhonePref(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<PhonePreference> listaPhone = phonePrefRepo.findAll();

        return gson.toJson(listaPhone);
    }
    //-------------------------------------------------
    //--------- PHONE PREFERENCE ----------------------


    @GetMapping("api/phonepref")
    @ResponseBody
    public String setPhonePref(@RequestParam String id, String pref){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("PHONE PREF ID: " + id + " PREF " + pref);
        List<Power> list = powerRepo.findAll();

        PhonePreference p = null;

        if (list.size() > 0){
            Power power = list.get(list.size()-1);

            p = new PhonePreference(id, pref);

            long powerStamp = power.getTimeStamp();
            long prefStamp = p.getTimeStamp();

            long time = 60 * 60 * 1000;
            if (Math.abs(prefStamp - powerStamp) > time){
                //non setto power perchè troppo lontano
                System.out.println("preferenza senza potenza associata");
            } else {
                p.setPower(power);
            }

            phonePrefRepo.save(p);

            return gson.toJson(p);

        } else {
            return "Non posso salvare la preferenza";
        }


    }


    //--------- PREFERENCE ----------------------------
    @GetMapping("/api/userpreference")
    @ResponseBody
    public String getUserPreference(@RequestParam String uid){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Preference> listaPreferenzeUser = preferenceRepo.findByUserId(uid);

        return gson.toJson(listaPreferenzeUser);
    }

    @GetMapping("/api/preference")
    @ResponseBody
    public String setPreference(@RequestParam String uid, int preferenza){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = findByUid(uid);

        List<Camera> listCamera = cameraRepo.findAll();
        List<Power> listPower = powerRepo.findAll();
        Camera camera = null;
        Power power = null;
        if ((listCamera.size() > 0) && (listPower.size() > 0)){
            camera = cameraRepo.findAll().get(cameraRepo.findAll().size()-1);
            power = powerRepo.findAll().get(powerRepo.findAll().size()-1);
        }

        Preference p = null;

        if (user != null && user.isInside()){
            Traccia t = TracceComponent.getTraccia();

            p = new Preference(user, t, preferenza);

            p.setPower(power);
            p.setCamera(camera);
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.toJson(user);

        return result;
    }

    // -------- SENSOR DATA ---------
    @GetMapping("/api/power")
    @ResponseBody
    public String getPower(@RequestParam String voltage, String current, String power){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Sensor Value : Voltage " + voltage + " current " + current + " power " + power);
        Power powerObj = new Power(Integer.valueOf(voltage), Double.valueOf(current), Integer.valueOf(power));

        //Si sceglie una caratteristica
        String car = "energy";
        //Si crea il testset file
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("dataset/test.arff"));
            writer.write("@relation "+ car + "\n@attribute POWER numeric\n@attribute CAR numeric\n\n@data\n");
            writer.write(powerObj.getPower() + ",?");
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        //SAVE ON DB
        this.powerRepo.save(powerObj);

        int powerInt = Integer.valueOf(power);
        if (powerInt < 70){
            InitComponent.restartList();
        }

        //PERFORM ML
        //if (MachineLearning.isInPlay() != true){
         //   MachineLearning machineLearning = new MachineLearning();
         //   machineLearning.performML(powerObj, car);
        //}

        if (MachineLearning2.isInPlay() != true){
            MachineLearning2 machineLearning2 = new MachineLearning2();
            machineLearning2.performML(powerObj);
        }


        return gson.toJson(powerObj);
    }


    @GetMapping("/api/camera")
    @ResponseBody
    public String fromCamera(@RequestParam String area, String number){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        try {
            tracceComponent.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
