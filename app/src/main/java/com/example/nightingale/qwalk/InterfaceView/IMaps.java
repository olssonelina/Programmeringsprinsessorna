package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;

/**
 * Created by Kraft on 2017-05-12.
 */

public interface IMaps {
    boolean checkLocationPermission();
    void focusOn(QLocation location);
    void close();
    boolean isOnScreen(QLocation location);
    void showOnMap(); // Minns inte vad detta innebär längre
    void pointArrowTo(QLocation location);
    void hideArrow();
    void placeMarker(Question question);
    void placeHiddenMarker(Question question);
    void enableMarker(Question question);
    void removeMarker(Question question);
    void showResults(Quiz quiz, Actor player, Actor bot);
    void showResults(Quiz quiz, Actor player);

}
