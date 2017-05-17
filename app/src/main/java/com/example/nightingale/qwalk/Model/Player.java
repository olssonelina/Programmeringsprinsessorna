package com.example.nightingale.qwalk.Model;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public class Player extends Actor {

    public Player(int quizLength) {
        super(quizLength);
    }


    public void setAnswer(int index, int answer) {
        answers[index] = answer;
    }

}
