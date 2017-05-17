package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class Bot extends Actor {

    Quiz quiz;
    List<Question> questions = quiz.getQuestions();
    ArrayList<Integer> correctAnswers = quiz.getCorrectAnswers();
    ArrayList<Integer> monkeyAnswers = new ArrayList<>();

    public Bot(Quiz quiz, int level) {
        super(0);
        this.quiz = quiz;
    }


    public int getScore(ArrayList<Integer> correctAnswers, ArrayList<Integer> monkeyAnswers) {
        int score = 0;

        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == monkeyAnswers.get(i)) {
                score++;
            }
        }

        return score;
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
