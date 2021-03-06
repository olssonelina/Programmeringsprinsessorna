package com.example.nightingale.qwalk.Model.Question;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nightingale.qwalk.Model.QLocation;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public abstract class Question implements Parcelable {

    private String questionTitle;
    private int correctAnswer;
    private QLocation location;
    private int questionID;

    /**
     * Constructs a cuestion
     * @param questionTitle
     * @param correctAnswer
     * @param location
     * @param questionID
     */
    protected Question(String questionTitle, int correctAnswer, QLocation location, int questionID) {
        this.questionID = questionID;
        this.questionTitle = questionTitle;
        this.correctAnswer = correctAnswer;
        this.location = location;
    }

    /**
     * @return returns latitude
     */
    public final double getLatitude() {
        return location.getLatitude();
    }

    public final int getQuestionID() {
        return questionID;
    }

    /**
     * @return returns longitude
     */
    public final double getLongitude() {
        return location.getLongitude();
    }

    /**
     * @return returns correct answer, whether it is index or the actual answer is different
     */
    public final int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @return return location
     */
    public final QLocation getLocation() {
        return location;
    }

    /**
     * @return returns question title
     */
    public final String getQuestionTitle() {
        return questionTitle;
    }

    /**
     * Static method for checking if a question title is allowed
     *
     * @param question string to test
     * @return true if allowes in a question
     */
    public static boolean validateQuestion(String question) {
        return !question.equals("");
    }

    /**
     * Static method for checking if a location is allowed
     *
     * @param latitude  latitude to test
     * @param longitude longitude to test
     * @return true if location is allowed in a question
     */
    public static boolean validateLocation(double latitude, double longitude) {
        return latitude != 0 && longitude != 0;
    }

    /**
     * @return returns the uppermost possible index/answer of this questions options/answer range
     */
    public abstract int getUpperBounds();

    /**
     * @return returns the lowermost possible index/answer of this questions options/answer range
     */
    public abstract int getLowerBounds();

    /**
     * {@inheritDoc}
     */
    protected Question(Parcel in) {
        questionID = in.readInt();
        questionTitle = in.readString();
        correctAnswer = in.readInt();
        location = (QLocation) in.readValue(QLocation.class.getClassLoader());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionID);
        dest.writeString(questionTitle);
        dest.writeInt(correctAnswer);
        dest.writeValue(location);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public static Parcelable.Creator<Tiebreaker> CREATOR = new Parcelable.Creator<Tiebreaker>() {
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
