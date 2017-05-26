package com.example.nightingale.qwalk.Model.Question;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nightingale.qwalk.Model.QLocation;

/**
 * Created by Elina Olsson on 2017-05-08.
 */

public class Tiebreaker extends Question implements Parcelable {

    private int upperBounds, lowerBounds;

    /**
     * Creates a tiebreaker for the Qwalk game
     *
     * @param questionTitle title of the question, the actual question
     * @param answer        the correct answer to this tiebreaker
     * @param latitude      the latitude of the tiebreaker
     * @param longitude     the longitude of the tiebreaker
     * @param lowerBounds   the lowest possible answer in the range of answers
     * @param upperBounds   the highest possible answer in the range of answers
     * @param ID            the id stored in the database for this question
     */
    public Tiebreaker(String questionTitle, int answer, double latitude, double longitude, int lowerBounds, int upperBounds, int ID) {
        super(questionTitle, answer, new QLocation(latitude, longitude), ID);
        this.upperBounds = upperBounds;
        this.lowerBounds = lowerBounds;
    }

    @Override
    public int getLowerBounds() {
        return lowerBounds;
    }

    @Override
    public int getUpperBounds() {
        return upperBounds;
    }

    /**
     * A static method to check if the specified bounds are allowed in a tiebreaker
     *
     * @param lower the lowest possible answer in the range of answers
     * @param upper the highest possible answer in the range of answers
     * @return returns true if inputs are allowed
     */
    public static boolean validateBounds(int lower, int upper) {
        return lower < upper;
    }

    /**
     * A static method to check if the specified bounds and answer are allowed in a tiebreaker
     *
     * @param lower  the lowest possible answer in the range of answers
     * @param answer the answer to the tiebreaker
     * @param upper  the highest possible answer in the range of answers
     * @return returns true if inputs are allowed
     */
    public static boolean validateAnswer(int lower, int answer, int upper) {
        return lower <= answer && answer <= upper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Tiebreaker) {
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
        super(in);
        lowerBounds = in.readInt();
        upperBounds = in.readInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(lowerBounds);
        dest.writeInt(upperBounds);
    }

    /**
     * {@inheritDoc}
     */
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