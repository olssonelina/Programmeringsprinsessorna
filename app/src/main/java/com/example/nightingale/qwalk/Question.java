package com.example.nightingale.qwalk;

import android.media.Image;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class Question {

    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;

    public Question(String title, String option1, String option2, String option3, String option4, int correctAnswer/*Image image, Position position*/ ) {
        this.questionTitle = title;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

}
