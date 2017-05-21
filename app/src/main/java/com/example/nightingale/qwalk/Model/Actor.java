package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public abstract class Actor {

    protected ArrayList<Integer> answers;

    public Actor(){}

    //public Actor(int quizLength) {
    //    this.answers = new int[quizLength];
    //}

    public int getScore() {
        return 0;
    }

    public ArrayList<Integer> getAnswers() {
       return answers;
    }
}
