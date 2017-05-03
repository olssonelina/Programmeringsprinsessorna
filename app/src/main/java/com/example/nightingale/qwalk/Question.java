package com.example.nightingale.qwalk;

import android.location.Location;
import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class Question {

    public static ArrayList<Question> questionsToSend = new ArrayList<Question>();

    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;
    private Location location;

    public Question(String title, String option1, String option2, String option3, String option4, int correctAnswer, double latitude, double longitude/*Image image, Position position*/ ) {
        this.questionTitle = title;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }

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

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
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

    public static ArrayList<Question> getQuestionsToSend(){
        return questionsToSend;
    }
}
