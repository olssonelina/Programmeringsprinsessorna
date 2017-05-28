package com.example.nightingale.qwalk.Model.Actor;

import com.example.nightingale.qwalk.Model.Actor.IActor;
import com.example.nightingale.qwalk.Model.QLocation;

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
        for (int i = 0; i < answers.length ; i++) {
            answers[i] = NO_ANSWER;
        }
    }

    /**
     * {@inheritDoc}
     */
    public final QLocation getLocation() {
        return location;
    }

    /**
     * {@inheritDoc}
     */
    public final void setLocation(QLocation location) {
        this.location = location;
    }

    /**
     * {@inheritDoc}
     */
    public final void setAnswer(int index, int answer) {
        answers[index] = answer;
    }

    /**
     * {@inheritDoc}
     */
    public final int[] getAnswers() {
        return answers.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getAnswer(int index) {
        return answers[index];
    }

}
