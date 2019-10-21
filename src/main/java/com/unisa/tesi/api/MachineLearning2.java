package com.unisa.tesi.api;

import com.unisa.tesi.model.Power;
import com.unisa.tesi.model.Predicted;
import com.unisa.tesi.model.Traccia;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MachineLearning2 {

    private static boolean inPlay = false;

    private ArrayList<Traccia> lista = InitComponent.getMusicList();
    private ArrayList<Traccia> orderByDance= InitComponent.getMusicList();

    public static boolean isInPlay() {
        return inPlay;
    }

    public static void setInPlay(boolean inPlay) {
        MachineLearning2.inPlay = inPlay;
    }

    public MachineLearning2(){

        Collections.sort(orderByDance, new Comparator<Traccia>() {
            @Override
            public int compare(Traccia o1, Traccia o2) {
                if (o1.getDanceability() == o2.getDanceability()){
                    return 0;
                } else if (o1.getDanceability() > o2.getDanceability()){
                    return 1;
                }
                return -1;
            }
        });

    }

    public void performML(Power power){
        try{
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("dataset/danceability.arff");
            ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("dataset/valence.arff");
            Instances dataset = source.getDataSet();
            Instances dataset2 = source2.getDataSet();

            ConverterUtils.DataSource testSource = new ConverterUtils.DataSource("dataset/test.arff");
            Instances testSet = testSource.getDataSet();

            dataset.setClassIndex(dataset.numAttributes() - 1);
            dataset2.setClassIndex(dataset2.numAttributes() - 1);
            testSet.setClassIndex(testSet.numAttributes() - 1);

            RandomForest rf = new RandomForest();
            rf.setNumIterations(10);
            rf.buildClassifier(dataset);

            Evaluation eval = new Evaluation(dataset);
            eval.evaluateModel(rf, testSet);
            Predicted predicted = new Predicted();
            for (Prediction prediction : eval.predictions()){
                predicted.setDanceability((float) prediction.predicted());
            }

            rf.buildClassifier(dataset2);
            eval = new Evaluation(dataset2);
            eval.evaluateModel(rf, testSet);
            for (Prediction prediction : eval.predictions()){
                predicted.setValence((float) prediction.predicted());
            }
            System.out.println("Predicted values:");
            System.out.println(predicted);
            Traccia t = findSong(predicted);
            System.out.println("Song found");
            System.out.println(t);

            playSong(t);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Traccia findSong(Predicted predicted) {
        Traccia t = orderByDance.get(0);
        ArrayList<Traccia> playedSong = InitComponent.getPlayedSong();
        Predicted first = new Predicted(orderByDance.get(0).getDanceability(), orderByDance.get(0).getValence());
        double min = distanceM(predicted, first);
        for (Traccia traccia : orderByDance){
            Predicted p = new Predicted(traccia.getDanceability(), traccia.getValence());
            double distance = distanceM(predicted, p);
            if (distance < min){
                min = distance;
                t = traccia;
            }
        }
        playedSong.add(t);
        lista.remove(t);
        return t;
    }

    public double distanceM(Predicted p1, Predicted p2){
        double resultQuad = Math.pow(p1.getDanceability() - p2.getDanceability(), 2) + Math.pow(p1.getValence() - p2.getValence(),2);
        double result = Math.sqrt(resultQuad);
        return result;
    }

    private void playSong(Traccia traccia) {
        inPlay = true;
        String[] args = new String[]{"/bin/zsh", "-c", "spotify play " + traccia.getUrl()};
        try {
            Process proc = new ProcessBuilder(args).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            Thread.sleep(traccia.getDuration());
            inPlay = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
