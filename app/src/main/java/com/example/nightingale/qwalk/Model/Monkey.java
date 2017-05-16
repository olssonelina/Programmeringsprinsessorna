package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class Monkey extends Actor {

    Quiz quiz;

    public Monkey(Quiz quiz, int level) {
        super(0);
        this.quiz = quiz;
    }


    public ArrayList<Integer> setAnswers(int level) {
        ArrayList<Integer> correctAnswers = quiz.getCorrectAnswers();
        ArrayList<Integer> monkeyAnswers = new ArrayList<>();

        for (int i = 0; i < correctAnswers.size(); i++) {

        }
        
        return monkeyAnswers;
    }

}
