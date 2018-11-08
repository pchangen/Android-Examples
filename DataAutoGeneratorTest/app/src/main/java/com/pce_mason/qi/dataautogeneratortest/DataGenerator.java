package com.pce_mason.qi.dataautogeneratortest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DataGenerator {
    private Context context;
    private Timer generateTimer = null;
    private int generateInterval = 1000;

    private int highestValue = 170;
    private int lowestValue = 50;
    private int hearRate = 70;
    private int randomIncrease,randomVector;

    public DataGenerator(Context context){
        this.context = context;
    }
    private String dataUpdater(){
        randomIncrease = new Random().nextInt(6); // 0 ~ 5
        randomVector = new Random().nextInt(3) - 1; // -1, 0, 1
        hearRate = hearRate + (randomVector * randomIncrease);
        if (hearRate > highestValue){
            hearRate -= 4;
        }else if(hearRate < lowestValue){
            hearRate += 4;
        }
        return String.valueOf(hearRate);
    }
    private void generateDataBroadcaster(){
        Intent intent = new Intent("heartData");
        intent.putExtra("heartRate",dataUpdater());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    private TimerTask dataGenerateTimerTaskMaker() {
        TimerTask dataTimerTask = new TimerTask() {
            @Override
            public void run() {
                generateDataBroadcaster();
            }
        };

        return dataTimerTask;
    }
    public boolean getGenerateState(){
        if (generateTimer == null){
            return true;
        }else {
            return false;
        }
    }
    public void startDataGenerate(){
        generateTimer = new Timer();
        generateTimer.schedule(dataGenerateTimerTaskMaker(), 0, generateInterval);
    }

    public void stopDataGenerate(){
        if (generateTimer != null) {
            generateTimer.cancel();
            generateTimer = null;
        }
    }

/*
    How to use
    --- On activity, The Capital Character need to change. for example the CONTEXT need to change like MainActivity.this
    DataGenerator VARIABLE_NAME = new DataGenerator(CONTEXT);
    VARIABLE_NAME
    if (VARIABLE_NAME.getGenerateState()) {
        VARIABLE_NAME.startDataGenerate();
    }else{
        VARIABLE_NAME.stopDataGenerate();
    }

*/
}
