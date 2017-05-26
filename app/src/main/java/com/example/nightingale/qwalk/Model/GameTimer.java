package com.example.nightingale.qwalk.Model;

/**
 * Created by PiaLocal on 2017-05-10.
 */

public class GameTimer {
    private boolean TimerRunning = false;
    private long tStart, tStop, tSaved;

    /**
     * Starts counting time from this moment.
     */
    public final void startTimer() {
        tStart = System.currentTimeMillis();
        TimerRunning = true;
        tSaved = 0;
    }

    /**
     * Stops counting time but saves current result for eventual "Resume".
     */
    public final void stopTimer() {
        if (TimerRunning) {
            tStop = System.currentTimeMillis();
            TimerRunning = false;
            tSaved += tStop - tStart;
        }
    }

    /**
     * Continue counting time from the last stop.
     */
    public final void resumeTimer() {
        if (!TimerRunning) {
            tStart = System.currentTimeMillis();
            TimerRunning = true;
        }
    }

    /**
     * Returns time (in seconds) counted since latest Start.
     *
     * @return time (in seconds) counted since latest Start
     */
    public final long getTime() {
        if (TimerRunning) {
            tStop = System.currentTimeMillis();
            return (tStop - tStart + tSaved) / 1000;
        }
        return (tSaved) / 1000;
    }
}
