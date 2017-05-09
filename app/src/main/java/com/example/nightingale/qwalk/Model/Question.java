package com.example.nightingale.qwalk.Model;

import android.location.Location;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public abstract class Question {

    String questionTitle;
    int correctAnswer;
    Location location;


    public void setLocation(double latitude, double longitude) {
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
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

    public Location getLocation() {
        return location;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

}
