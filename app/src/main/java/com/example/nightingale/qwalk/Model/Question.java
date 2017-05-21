package com.example.nightingale.qwalk.Model;

import android.location.Location;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public abstract class Question {

    //TODO gör fält privata, gör en gemensam konstruktor (ger dock parcel-problem, vi får lösa det)

    protected String questionTitle;
    protected int correctAnswer;
    protected QLocation location;
    protected int upperBounds;
    protected int lowerBounds;


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

    public static boolean validateQuestion(String question){
        return !question.equals("");
    }

    public static boolean validateLocation(double latitude, double longitude){
        return latitude != 0 && longitude != 0;
    }

    public int getUpperBounds(){return upperBounds;}

    public int getLowerBounds(){return lowerBounds;}


    @Override
    public abstract boolean equals(Object o);
    //public QLocation getQLocation() {return new QLocation(location); };

}
