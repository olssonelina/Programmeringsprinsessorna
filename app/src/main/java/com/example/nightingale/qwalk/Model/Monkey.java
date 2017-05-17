package com.example.nightingale.qwalk.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class Monkey extends Actor {

    Quiz quiz;

    public Monkey(Quiz quiz, int level) {
        super(0);
        this.quiz = quiz;
    }


    public int getScore(ArrayList<Integer> correctAnswers, ArrayList<Integer> monkeyAnswers) {
        int score = 0;

        for (int i = 0; i < correctAnswers.size() ; i++) {
            if (correctAnswers.get(i) == monkeyAnswers.get(i)) {
                score++;
            }
        }

        return score;
    }


    public ArrayList<Integer> setAnswers(int level, int numberOfOptions) {
        ArrayList<Integer> correctAnswers = quiz.getCorrectAnswers();
        ArrayList<Integer> monkeyAnswers;

        monkeyAnswers = randomAnswers(correctAnswers.size(), numberOfOptions);
        monkeyAnswers = placeCorrectAnswers(monkeyAnswers, level);

        return monkeyAnswers;
    }

    /**
     * Generates a list with random answers.
     *
     * @param listLength      Length of the list for correct answers.
     * @param numberOfOptions Number of how many options that is used for a question.
     * @return List of randomly places answers.
     */

    //TODO Hur ska apan veta hur många alternativ som finns att svara på?
    private ArrayList<Integer> randomAnswers(int listLength, int numberOfOptions) {
        ArrayList<Integer> randomList = new ArrayList<>();
        Random rand = new Random();
        int range = numberOfOptions + 1;

        for (int i = 0; i < listLength; i++) {
            randomList.add(rand.nextInt(range));
        }
        return randomList;
    }

    /**
     * Generates a list with randomly placed correct answers.
     *
     * @param correctAnswers List of correct answers.
     * @param level          Difficulty level
     * @return List with randomly placed correct answers
     */


    private ArrayList<Integer> placeCorrectAnswers(ArrayList<Integer> correctAnswers, int level) {
        ArrayList<Integer> newList = new ArrayList<>();
        List<Integer> randomIndex = new ArrayList<>();

        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            randomIndex.add(i);
        }

        Collections.shuffle(randomIndex);
        randomIndex = randomIndex.subList(0, level);

        for (int j = 0; j < randomIndex.size(); j++) {
            newList.add(randomIndex.get(j), correctAnswers.get(randomIndex.get(j)));
        }

        return newList;
    }


}
