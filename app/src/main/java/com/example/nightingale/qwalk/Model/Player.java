package com.example.nightingale.qwalk.Model;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public class Player extends Actor {

    private QLocation userLocation = new QLocation(0, 0);

    public Player() {
        //super(quizLength);
    }

    public QLocation getLocation(){return userLocation;}

    public void updateLocation(QLocation userLocation){this.userLocation = userLocation;}

    public void setAnswer(int index, int answer) {
        answers.add(index, answer);
    }

}
