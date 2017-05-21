package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;

import java.util.ArrayList;

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
    void showResults(Quiz quiz, int[] playerAnswers, ArrayList<Integer> aiAnswers, long quizTime);
    void setShowClosestEnabled(boolean value);
    void initializeAi(QLocation location);
    void moveAi(QLocation location);
    void setProgress(int current, int total);

}
