package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class AI implements Runnable, IActor {

    private int[] answers;
    private int difficulty;
    private QLocation location;
    private QLocation[] questionLocations;

    private GameTimer timer = new GameTimer();
    private List<IOnAIMoveListener> listeners = new ArrayList<>();

    /**
     * Creates an ai which aims to answer questions in a quiz and to walk around on the map
     * @param correctAnswers the correct answers to a quiz, in order of index
     * @param tieBreaker true if the quiz has a tiebreaker
     * @param low the correct answers to the quiz, in order of index
     * @param high the upper bounds of every question in the quiz, in order of index
     * @param difficulty the difficulty of the quiz in percent, 1 - 100.
     * @param questionLocations the question locations in the quiz, in order of index
     */
    public AI(int[] correctAnswers, boolean tieBreaker, int[] low, int[] high, int difficulty, QLocation[] questionLocations) {
        this.difficulty=difficulty;
        this.answers = new int[correctAnswers.length];
        for (int a: answers) {
            a = NO_ANSWER;
        }
        this.questionLocations = questionLocations;
        setAnswers(correctAnswers, tieBreaker, low, high);
        timer.startTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore(int[] correctAnswers) {
        int correctCount = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (correctAnswers[i] == answers[i]){
                correctCount++;
            }
        }
        return correctCount;
    }


    /**
     * {@inheritDoc}
     */
    public int getAnswer(int index) {
        return answers[index];
    }

    /**
     * {@inheritDoc}
     */
    public int[] getAnswers(){
        return answers;
    }

    private void setAnswers(int[] correctAnswers, boolean tiebreaker, int[] low, int[] high) {
        for (int i = 0; i < answers.length; i++) {
            if (difficulty > randomInt()) {
                answers[i] = correctAnswers[i];
            } else {
                answers[i] = randomAnswer((high[i]+1));
            }
        }
        if(tiebreaker){
            int lastIndex=correctAnswers.length - 1;
            answers[lastIndex] =randomAnswer(high[lastIndex]-low[lastIndex])+low[lastIndex];
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
        //TODO gör någon loop där den hela tiden ändrar positionen på ai och även notifiesListeners.
        //TODO målet är att apan ska röra sig mot frågorna, mha questionlocations
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAnswer(int index, int answer) {
        throw new IllegalAccessError("Calling this method in ai is not allowed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QLocation getLocation() {
        return location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocation(QLocation location) {
        this.location = location;
    }

    void setOnAImovedListener(IOnAIMoveListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(QLocation location) {
        for (IOnAIMoveListener l:listeners) {
            l.AIMoved(location);
        }
    }
}