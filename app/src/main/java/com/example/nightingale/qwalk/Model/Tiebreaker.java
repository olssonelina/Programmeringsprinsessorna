package com.example.nightingale.qwalk.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class Tiebreaker extends Question implements Parcelable {

    private int lowerBounds;
    private int upperBounds;

    public Tiebreaker(String questionTitle, int answer, double longitude, double latitude, int lowerBounds, int upperBounds){
        this.questionTitle = questionTitle;
        this.correctAnswer = answer;
        setLocation(latitude, longitude);
        this.upperBounds = upperBounds;
        this.lowerBounds = lowerBounds;
    }

    public int getLowerBounds() {
        return lowerBounds;
    }

    public int getUpperBounds() {
        return upperBounds;
    }

    public static boolean validateBounds(int lower, int upper){
        return lower < upper;
    }

    public static boolean validateAnswer(int lower, int answer, int upper){
        return lower <= answer && answer <= upper;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tiebreaker){
            Tiebreaker other = (Tiebreaker) o;
            return other.getLowerBounds() == getLowerBounds() &&
                    other.getUpperBounds() == getUpperBounds() &&
                    other.getCorrectAnswer() == getCorrectAnswer() &&
                    other.getLocation().equals(getLocation()) &&
                    other.getQuestionTitle().equals(getQuestionTitle());
        }
        return false;
    }


    protected Tiebreaker(Parcel in) {
        lowerBounds = in.readInt();
        upperBounds = in.readInt();
        questionTitle = in.readString();
        correctAnswer = in.readInt();
        location = (QLocation) in.readValue(QLocation.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lowerBounds);
        dest.writeInt(upperBounds);
        dest.writeString(questionTitle);
        dest.writeInt(correctAnswer);
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tiebreaker> CREATOR = new Parcelable.Creator<Tiebreaker>() {
        @Override
        public Tiebreaker createFromParcel(Parcel in) {
            return new Tiebreaker(in);
        }

        @Override
        public Tiebreaker[] newArray(int size) {
            return new Tiebreaker[size];
        }
    };
}