package com.example.nightingale.qwalk.Model;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by PiaLocal on 2017-05-10.
 */

public class GameTimer {

    private boolean TimerRunning=false;
    private long tStart, tStop, tSaved;
    

    void StartTimer(){
        tStart = System.currentTimeMillis();
        TimerRunning=true;
        tSaved=0;
    }


    void StopTimer(){
        if(TimerRunning){
            tStop = System.currentTimeMillis();
            TimerRunning=false;
        }
    }

    void PauseTimer(){
        if(TimerRunning){
            tStop = System.currentTimeMillis();
            TimerRunning=false;
            tSaved+=tStop-tStart;
        }
    }

    void ResumeTimer(){
        if(!TimerRunning){
            tStart = System.currentTimeMillis();
            TimerRunning=true;
        }
    }

    double GetTime(){
        if(TimerRunning){
            tStop = System.currentTimeMillis();
            return (tStop-tStart+tSaved)/1000.0;
        }
        return (tStop-tStart+tSaved)/1000.0;
    }
}
