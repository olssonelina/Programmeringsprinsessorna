package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.GameTimer;
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
    void showResults(Quiz quiz, ArrayList<Integer> playerAnswers, ArrayList<Integer> botAnswers, long quizTime);
    void setShowClosestEnabled(boolean value);
    void initializeBot(QLocation location);
    void moveBot(QLocation location);
    void setProgress(int current, int total);

}
