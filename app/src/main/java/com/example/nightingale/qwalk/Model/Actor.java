package com.example.nightingale.qwalk.Model;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public abstract class Actor {

    int[] answers;

    public Actor(){}

    public Actor(int quizLength) {
        this.answers = new int[quizLength];
    }

    public int getScore() {
        return 0;
    }

    public int[] getAnswers() {
       return answers;
    }
}
