package com.example.nightingale.qwalk.Model;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public class Player  {

    private QLocation userLocation = new QLocation(0, 0);
    private int[] answers;

    public Player(int quizLength) {
        answers = new int[quizLength];
    }

    public QLocation getLocation(){return userLocation;}

    public void updateLocation(QLocation userLocation){this.userLocation = userLocation;}

    public void setAnswer(int index, int answer) {
        answers[index] = answer;
    }

    public int[] getAnswers(){
        return answers;
    }

}
