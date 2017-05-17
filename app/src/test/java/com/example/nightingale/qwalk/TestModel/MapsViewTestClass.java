package com.example.nightingale.qwalk.TestModel;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Presenter.MapsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-05-17.
 */

public class MapsViewTestClass implements IMaps {

    public MapsPresenter presenter;

    public List<Question> placedMarkers = new ArrayList<>();
    public List<Question> placedHiddenMarkers = new ArrayList<>();
    public List<Question> enabledMarkers = new ArrayList<>();

    public QLocation bot;

    public void mapReady(){

    }

    public void userPositionUpdated(QLocation location){
        presenter.updateUserLocation(location);
    }

    public Question markerPressed(QLocation location){
        for (Question q: enabledMarkers) {
            if (q.getLocation().equals(location)){
                return q;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public boolean checkLocationPermission() {
        return true;
    }

    @Override
    public void focusOn(QLocation location) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isOnScreen(QLocation location) {
        return true;
    }

    @Override
    public void pointArrowTo(QLocation location) {

    }

    @Override
    public void hideArrow() {

    }

    @Override
    public void placeMarker(Question question) {
        placedMarkers.add(question);
    }

    @Override
    public void placeHiddenMarker(Question question) {
        placedHiddenMarkers.add(question);
    }

    @Override
    public void enableMarker(Question question) {
        enabledMarkers.add(question);
    }

    @Override
    public void removeMarker(Question question) {
        enabledMarkers.remove(question);
        placedHiddenMarkers.remove(question);
        placedMarkers.remove(question);
    }

    @Override
    public void showResults(Quiz quiz, Actor player, Actor bot) {

    }

    @Override
    public void showResults(Quiz quiz, Actor player) {

    }

    @Override
    public void initializeBot(QLocation location) {
        bot = location;
    }

    @Override
    public void moveBot(QLocation location) {
        bot = location;
    }
}