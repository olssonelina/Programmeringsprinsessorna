package com.example.nightingale.qwalk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class AI extends Actor implements Runnable {

    private int level;
    private int score;
    private GameTimer timer = new GameTimer();

    private List<IOnAIMoveListener> listeners = new ArrayList<>();


    public AI(ArrayList<Integer> correctAnswers, boolean tieBreaker, ArrayList<Integer> low, ArrayList<Integer> high, int level) {
        super(correctAnswers.size());
        this.level=level;
        setAnswers(correctAnswers, tieBreaker, low, high);
        setScore(correctAnswers);
        timer.startTimer();
    }


    private void setScore(ArrayList<Integer> correctAnswers) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == answers.get(i)) {
                score++;
            }
        }
    }

    public int getAnswer(int index) {
        return answers.get(index);
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfQuestions() {
        return answers.size();
    }

    private void setAnswers(ArrayList<Integer> correctAnswers, boolean tiebreaker, ArrayList<Integer> low, ArrayList<Integer> high) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (level > randomInt()) {
                answers.add(correctAnswers.get(i));
            } else {
                answers.add(randomAnswer(((high.get(i)+1))));
            }
        }
        if(tiebreaker){
            int lastIndex=correctAnswers.size()-1;
            answers.set(lastIndex,randomAnswer(high.get(lastIndex)-low.get(lastIndex))+low.get(lastIndex));
        }
    }

    private int randomInt() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    private int randomAnswer(int numberOfOptions) {
        Random rand = new Random();
        return rand.nextInt(numberOfOptions);
    }
    

    @Override
    public void run() {
            moveBot();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
    }

    public void moveBot() {
    }

    public void setOnAImovedListener(IOnAIMoveListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(QLocation location) {
        for (IOnAIMoveListener l:listeners) {
            l.AIMoved(location);
        }
    }
}