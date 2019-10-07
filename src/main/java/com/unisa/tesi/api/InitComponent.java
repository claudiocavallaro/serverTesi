package com.unisa.tesi.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unisa.tesi.model.PreferenceML;
import com.unisa.tesi.model.Traccia;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitComponent {

    private static ArrayList<PreferenceML> lista = new ArrayList<>();

    private static ArrayList<Traccia> musicList = new ArrayList<>();
    private static ArrayList<Traccia> playedSong = new ArrayList<>();

    public static ArrayList<Traccia> getPlayedSong() {
        return playedSong;
    }

    public static void setPlayedSong(ArrayList<Traccia> playedSong) {
        InitComponent.playedSong = playedSong;
    }

    public static ArrayList<PreferenceML> getLista() {
        return lista;
    }

    public static void setLista(ArrayList<PreferenceML> lista) {
        InitComponent.lista = lista;
    }

    public static ArrayList<Traccia> getMusicList() {
        return musicList;
    }

    public static void setMusicList(ArrayList<Traccia> musicList) {
        InitComponent.musicList = musicList;
    }

    public InitComponent(){
        Gson gson = new Gson();
        try {
            lista = gson.fromJson(new FileReader("db.json"), new TypeToken<List<PreferenceML>>(){}.getType());
            musicList = gson.fromJson(new FileReader("dbtott.json"), new TypeToken<List<Traccia>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restartList(){
        Gson gson = new Gson();
        playedSong = new ArrayList<>();
        try{
            musicList = gson.fromJson(new FileReader("dbtott.json"), new TypeToken<List<Traccia>>(){}.getType());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
