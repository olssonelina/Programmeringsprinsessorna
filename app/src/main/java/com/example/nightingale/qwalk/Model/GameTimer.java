package com.example.nightingale.qwalk.Model;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by PiaLocal on 2017-05-10.
 */

public class GameTimer {
    private GameTimer(){}
    private static boolean TimerRunning=false;
    private static long tStart;
    private static long tStop;

    static void StartTimer(){
        if(TimerRunning){
            //kasta exception? fler än en timer samtidigt?
        }
        tStart = System.currentTimeMillis();
    }

    static double StopTimer(){
        if(TimerRunning){
            tStop = System.currentTimeMillis();
            TimerRunning=false;
            return (tStop-tStart)/1000.0;
        }
        return 0;
    }
}
