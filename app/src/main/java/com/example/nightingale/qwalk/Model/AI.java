package com.example.nightingale.qwalk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class AI extends Actor  {

    private Quiz quiz;
    private List<Question> questions = quiz.getQuestions();
    private ArrayList<Integer> correctAnswers = quiz.getCorrectAnswers();
    private ArrayList<Integer> monkeyAnswers = new ArrayList<>();
    private int score;

    public AI(Quiz quiz, int level) {
        super(0);
        this.quiz = quiz;
    }


    public void setScore(ArrayList<Integer> correctAnswers, ArrayList<Integer> monkeyAnswers) {
        int score = 0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == monkeyAnswers.get(i)) {
                score++;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfQuestions() {
        return monkeyAnswers.size();
    }


    public ArrayList<Integer> setAnswers(int level) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (level > randomInt()) {
                monkeyAnswers.add(correctAnswers.get(i));
            } else {
                if (questions.get(i) instanceof OptionQuestion) {
                    monkeyAnswers.add(randomAnswer(((OptionQuestion) questions.get(i)).getNumberOfOptions()));
                }
            }
        }
        return monkeyAnswers;
    }

    private int randomInt() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    private int randomAnswer(int numberOfOptions) {
        Random rand = new Random();
        return rand.nextInt(numberOfOptions);
    }

}
