package com.example.nightingale.qwalk.Model;

/**
 * Created by Elina Olsson on 2017-05-12.
 */

public class Player implements IActor {

    private QLocation location;
    private int[] answers;

    /**
     * Creates a Qwalkplayer containing the players location and answers to questions
     *
     * @param quizLength
     */
    public Player(int quizLength) {
        answers = new int[quizLength];
        for (int a : answers) {
            a = NO_ANSWER;
        }
    }

    /**
     * {@inheritDoc}
     */
    public QLocation getLocation() {
        return location;
    }

    /**
     * {@inheritDoc}
     */
    public void setLocation(QLocation location) {
        this.location = location;
    }

    /**
     * {@inheritDoc}
     */
    public void setAnswer(int index, int answer) {
        answers[index] = answer;
    }

    /**
     * {@inheritDoc}
     */
    public int[] getAnswers() {
        return answers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAnswer(int index) {
        return answers[index];
    }

}
