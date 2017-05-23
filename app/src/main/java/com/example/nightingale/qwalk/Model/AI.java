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

    /**
     * Creates an ai which aims to answer questions in a quiz and to walk around on the map
     *
     * @param correctAnswers    the correct answers to a quiz, in order of index
     * @param tieBreaker        true if the quiz has a tiebreaker
     * @param low               the correct answers to the quiz, in order of index
     * @param high              the upper bounds of every question in the quiz, in order of index
     * @param difficulty        the difficulty of the quiz in percent, 1 - 100.
     * @param questionLocations the question locations in the quiz, in order of index
     */
    public AI(int[] correctAnswers, boolean tieBreaker, int[] low, int[] high, int difficulty, QLocation[] questionLocations, QLocation startLocation) {
        this.difficulty = difficulty;
        this.answers = new int[correctAnswers.length];
        for (int a : answers) {
            a = NO_ANSWER;
        }
        this.questionLocations = questionLocations;
        setAnswers(correctAnswers, tieBreaker, low, high);
        this.location = startLocation;
        timer.startTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore(int[] correctAnswers) {
        int correctCount = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (correctAnswers[i] == answers[i]) {
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
    public int[] getAnswers() {
        return answers;
    }

    private void setAnswers(int[] correctAnswers, boolean tiebreaker, int[] low, int[] high) {
        for (int i = 0; i < answers.length; i++) {
            if (difficulty > randomInt()) {
                answers[i] = correctAnswers[i];
            } else {
                answers[i] = randomAnswer((high[i] + 1));
            }
        }
        if (tiebreaker) {
            int lastIndex = correctAnswers.length - 1;
            answers[lastIndex] = randomAnswer(high[lastIndex] - low[lastIndex]) + low[lastIndex];
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

    /**
     * Moves the bot over the map with speed depending on time.
     */

    @Override
    public void run() {
        //TODO gör någon loop där den hela tiden ändrar positionen på ai och även notifiesListeners.
        //TODO målet är att apan ska röra sig mot frågorna, mha questionlocations

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        int maxTime = 300; //maxTime = totalMaxTime / numberOfQuestions

        /*
        for (int i = 0; i < questionLocations.length - 1; i++) {
            while (timer.getTime() < maxTime) {
                lat = lat + (questionLocations[i].deltaLat(questionLocations[i+1]) / maxTime);
                lng = lng + (questionLocations[i].deltaLong(questionLocations[i+1]) / maxTime);
                location = new QLocation(lat, lng);
                notifyListeners(location);*/

        for (int i = 0; i < 30; i++) {

            lat = lat + 0.0003;
            lng = lng + 0.0003;
            location = new QLocation(lat, lng);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }


        try {
            Thread.sleep(1000);
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
    public synchronized void setLocation(QLocation location) {
        this.location = location;
    }
}