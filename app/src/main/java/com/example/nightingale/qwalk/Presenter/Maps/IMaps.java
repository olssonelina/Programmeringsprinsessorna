package com.example.nightingale.qwalk.Presenter.Maps;

import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;

/**
 * Created by Kraft on 2017-05-12.
 */

public interface IMaps {
    void focusOn(QLocation location);

    void close();

    boolean isOnScreen(QLocation location);

    void pointArrowTo(QLocation location);

    void hideArrow();

    void placeMarker(Question question);

    void enableMarker(Question question);

    void removeMarker(Question question);

    void showResults(Quiz quiz, int[] playerAnswers, int[] aiAnswers, long quizTime);

    void setShowClosestEnabled(boolean value);

    void initializeAi(QLocation location);

    void moveAi(QLocation location);

    void setProgress(int current, int total);

}
