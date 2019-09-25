package com.unisa.tesi.api;

import com.unisa.tesi.model.Power;
import com.unisa.tesi.model.PreferenceML;
import com.unisa.tesi.model.Traccia;
import io.swagger.models.auth.In;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;

public class MachineLearning {

    private static boolean inPlay = false;

    public static boolean isInPlay() {
        return inPlay;
    }

    public static void setInPlay(boolean inPlay) {
        MachineLearning.inPlay = inPlay;
    }

    public MachineLearning(){}

    public void performML(Power power, String property){
        try{
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("dataset/"+ property +".arff");
            Instances dataset = source.getDataSet();

            ConverterUtils.DataSource testSource = new ConverterUtils.DataSource("dataset/test.arff");
            Instances testSet = testSource.getDataSet();

            dataset.setClassIndex(dataset.numAttributes() - 1);
            testSet.setClassIndex(testSet.numAttributes() - 1);

            RandomForest rf = new RandomForest();
            rf.setNumIterations(10);
            rf.buildClassifier(dataset);
            Evaluation eval = new Evaluation(dataset);
            eval.evaluateModel(rf, testSet);

            for (Prediction prediction : eval.predictions()){
                findSong(prediction.predicted(), property);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void findSong(double predicted, String car) {
        ArrayList<PreferenceML> lista = InitComponent.getLista();
        double min = predicted - 0;
        Traccia t = null;
        for (PreferenceML preferenceML : lista){
            float musicValue = preferenceML.getNumber(car);
            double distance = Math.abs(musicValue - predicted);
            if (distance < Math.abs(min)){
                min = distance;
                t = preferenceML.getTraccia();
            }
        }
        playSong(t);
    }

    private void playSong(Traccia traccia) {
        inPlay = true;
        String[] args = new String[]{"/bin/bash", "-c", "spotify play " + traccia.getUrl()};
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
