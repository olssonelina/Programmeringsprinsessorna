package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public abstract class Actor {

    protected ArrayList<Integer> answers = new ArrayList<>();

    public Actor(int quizLength){
        for(int i=0; i<quizLength;i++) {
            answers.add(-1);
        }
    }

    //public int getScore() {
    //    return 0;
    //}

    public ArrayList<Integer> getAnswers() {
       return answers;
    }
}
