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
     * Moves the bot over the map from question to question.
     */
    @Override
    public void run() {

        int sleepTime;

        switch (difficulty) {
            default:
                sleepTime = 850;
                break;
            case 50:
                sleepTime = 780;
                break;
            case 75:
                sleepTime = 700;
                break;
        }

        for (QLocation q : questionLocations) {
            double deltaLatitude = location.deltaLat(q); // Calculate the total distance in each axis
            double deltaLongitude = location.deltaLong(q);
            int distance = (int) Math.round(location.distanceTo(q)); // Calculate the distance (in meters) between current location and next question

            double stepLatitude = deltaLatitude / (distance); // Calculate how far the ai will move with every step
            double stepLongitude = deltaLongitude / (distance);

            for (int i = 0; i < distance; i++) { // For every meter, sleep and walk until location is at current question
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }

                location = new QLocation(location.getLatitude() + stepLatitude, location.getLongitude() + stepLongitude);
            }

            location = q; //Adjust for rounding errors

            try {
                Thread.sleep(sleepTime * 10);
            } catch (InterruptedException e) {
            } // Let the ai "wait" while it is "answering" the question

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