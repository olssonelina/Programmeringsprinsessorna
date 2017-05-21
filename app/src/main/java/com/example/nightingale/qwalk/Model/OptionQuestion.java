package com.example.nightingale.qwalk.Model;

import android.graphics.Path;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class OptionQuestion extends Question implements Parcelable  {

    private String option1;
    private String option2;
    private String option3;
    private String option4;


    public OptionQuestion(String title, String option1, String option2, String option3, String option4, int correctAnswer, double latitude, double longitude/*Image image, Position position*/ ) {
        this.questionTitle = title;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        location = new QLocation(latitude, longitude);
        this.upperBounds=3;
        this.lowerBounds=0;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof OptionQuestion){
            OptionQuestion other = (OptionQuestion) o;

            return other.getOption1().equals(getOption1()) &&
                    other.getOption2().equals(getOption2()) &&
                    other.getOption3().equals(getOption3()) &&
                    other.getOption4().equals(getOption4()) &&
                    other.getCorrectAnswer() == getCorrectAnswer() &&
                    other.getLocation().equals(getLocation()) &&
                    other.getQuestionTitle().equals(getQuestionTitle());
        }
        return false;
    }

    protected OptionQuestion(Parcel in) {
        questionTitle = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        correctAnswer = in.readInt();
        location = (QLocation) in.readValue(QLocation.class.getClassLoader());
    }

    public static boolean validateOptions(String[] options){
        int count = 0;
        for (int i = 0; i < options.length; i++) {
            if (!options[i].equals("")) {
                count++;
            }
        }
        return count >= 2;
    }

    public int getNumberOfOptions() {
        int numberOfOptions = 0;
        String[] options = new String[4];
        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        options[3] = option4;

        for (int i = 0; i < options.length ; i++) {
            if (!options[i].equals("")) {
                numberOfOptions++;
            }
        }
        return numberOfOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionTitle);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(correctAnswer);
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OptionQuestion> CREATOR = new Parcelable.Creator<OptionQuestion>() {
        @Override
        public OptionQuestion createFromParcel(Parcel in) {
            return new OptionQuestion(in);
        }

        @Override
        public OptionQuestion[] newArray(int size) {
            return new OptionQuestion[size];
        }
    };
}