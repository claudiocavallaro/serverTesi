package com.unisa.tesi.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unisa.tesi.model.PreferenceML;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitComponent {

    private static ArrayList<PreferenceML> lista = new ArrayList<>();

    public static ArrayList<PreferenceML> getLista() {
        return lista;
    }

    public static void setLista(ArrayList<PreferenceML> lista) {
        InitComponent.lista = lista;
    }

    public InitComponent(){
        Gson gson = new Gson();
        try {
            lista = gson.fromJson(new FileReader("db.json"), new TypeToken<List<PreferenceML>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
