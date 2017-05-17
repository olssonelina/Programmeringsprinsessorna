package com.example.nightingale.qwalk.Model;

import android.location.Location;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public abstract class Question {

    //TODO gör fält privata, gör en gemensam konstruktor (ger dock parcel-problem, vi får lösa det)

    String questionTitle;
    int correctAnswer;
    QLocation location;


    public void setLocation(double latitude, double longitude) {
        location = new QLocation(latitude, longitude);
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public QLocation getLocation() {
        return location;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    @Override
    public abstract boolean equals(Object o);
    //public QLocation getQLocation() {return new QLocation(location); };

}
