package com.unisa.tesi.api;

import com.unisa.tesi.model.Traccia;
import com.unisa.tesi.persistence.TracciaRepo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class TracceComponent implements DisposableBean, Runnable{

    private TracciaRepo tracciaRepo;
    private Thread thread;
    private volatile boolean flag = true;

    public TracceComponent(TracciaRepo tracciaRepo){
        this.thread = new Thread(this);
        this.tracciaRepo = tracciaRepo;
        this.thread.start();
    }


    @Override
    public void run() {
        while(flag == true){

            List<Traccia> listaTracce = tracciaRepo.findAll();

            Collections.sort(listaTracce, new Comparator<Traccia>() {
                @Override
                public int compare(Traccia o1, Traccia o2) {
                    return Float.compare(o2.getEnergy(), o1.getEnergy());
                }
            });

            for (Traccia t : listaTracce){
                System.out.println(t.toString());

                double second = Double.valueOf(t.getDuration());
                int intPart = (int) second;
                intPart = intPart+1;

                String[] args = new String[] {"/bin/bash", "-c", "spotify play " + t.getUrl()};

                try {
                    Process proc = new ProcessBuilder(args).start();

                } catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    Thread.sleep(intPart*1000);
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    @Override
    public void destroy() throws Exception {

    }





}
