package com.example.nightingale.qwalk.Model;

/**
 * Created by Kraft on 2017-05-23.
 */

public interface IActor {

    /**
     * Code for no answer
     */
    public static final int NO_ANSWER = -1;

    /**
     * Gets a specific answer
     *
     * @param index the index of the question
     * @return returns the answer to the question specified by the index
     */
    int getAnswer(int index);

    /**
     * Sets an answer to a question
     *
     * @param index  index of the question to answer
     * @param answer the answer to the question
     */
    void setAnswer(int index, int answer);

    /**
     * @return returns all answers
     */
    int[] getAnswers();

    /**
     * @return returns the location of the object
     */
    QLocation getLocation();

    /**
     * Sets the location of an object
     *
     * @param location
     */
    void setLocation(QLocation location);

}
