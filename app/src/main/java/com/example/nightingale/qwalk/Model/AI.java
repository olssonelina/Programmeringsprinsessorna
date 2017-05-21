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

    ArrayList<Integer> monkeyAnswers = new ArrayList<>();
    int level;

    private int score;
    GameTimer timer = new GameTimer();

    List<IOnAIMoveListener> listeners = new ArrayList<>();


    public AI(ArrayList<Integer> correctAnswers, boolean tieBreaker, ArrayList<Integer> low, ArrayList<Integer> high, int level) {
        this.level=level;
        setAnswers(correctAnswers, tieBreaker, low, high);
        setScore(correctAnswers);
        timer.startTimer();
    }


    private void setScore(ArrayList<Integer> correctAnswers) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == monkeyAnswers.get(i)) {
                score++;
            }
        }
    }

    public int getAnswer(int index) {
        //int index = quiz.getQuestionIndex(question);
        return monkeyAnswers.get(index);
    }

    public ArrayList<Integer> getAnswers(){
        return monkeyAnswers;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfQuestions() {
        return monkeyAnswers.size();
    }

    private void setAnswers(ArrayList<Integer> correctAnswers, boolean tiebreaker, ArrayList<Integer> low, ArrayList<Integer> high) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (level > randomInt()) {
                monkeyAnswers.add(correctAnswers.get(i));
            } else {
                monkeyAnswers.add(randomAnswer(((high.get(i)+1))));
            }
        }
        if(tiebreaker){
            int lastIndex=correctAnswers.size()-1;
            monkeyAnswers.set(lastIndex,randomAnswer(high.get(lastIndex)-low.get(lastIndex))+low.get(lastIndex));
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