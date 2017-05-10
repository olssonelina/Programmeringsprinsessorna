package com.example.nightingale.qwalk.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class OptionQuestion extends Question implements Parcelable  {

    public static ArrayList<OptionQuestion> questionsToSend = new ArrayList<OptionQuestion>();

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
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
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


    public static ArrayList<OptionQuestion> getQuestionsToSend(){
        return questionsToSend;
    }

    public static void wipeQuestionsToSend(){
        questionsToSend = new ArrayList<OptionQuestion>();
    }


    protected OptionQuestion(Parcel in) {
        questionTitle = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        correctAnswer = in.readInt();
        location = (Location) in.readValue(Location.class.getClassLoader());
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