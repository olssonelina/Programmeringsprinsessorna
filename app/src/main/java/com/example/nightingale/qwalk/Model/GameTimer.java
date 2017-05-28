package com.example.nightingale.qwalk.Model;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class GameTimer {
    private boolean timerRunning = false;
    private long tStart, tStop, tSaved;

    /**
     * Starts counting time from this moment.
     */
    public final void startTimer() {
        tStart = System.currentTimeMillis();
        timerRunning = true;
        tSaved = 0;
    }

    /**
     * Stops counting time but saves current result for eventual "Resume".
     */
    public final void stopTimer() {
        if (timerRunning) {
            tStop = System.currentTimeMillis();
            timerRunning = false;
            tSaved += tStop - tStart;
        }
    }

    /**
     * Continue counting time from the last stop.
     */
    public final void resumeTimer() {
        if (!timerRunning) {
            tStart = System.currentTimeMillis();
            timerRunning = true;
        }
    }

    /**
     * Returns time (in seconds) counted since latest Start.
     *
     * @return time (in seconds) counted since latest Start
     */
    public final long getTime() {
        if (timerRunning) {
            tStop = System.currentTimeMillis();
            return (tStop - tStart + tSaved) / 1000;
        }
        return (tSaved) / 1000;
    }
}
