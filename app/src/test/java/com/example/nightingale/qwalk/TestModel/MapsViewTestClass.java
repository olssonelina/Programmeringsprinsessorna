package com.example.nightingale.qwalk.TestModel;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
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
        presenter.mapIsReady();
    }

    public void userPositionUpdated(QLocation location){
        presenter.updateUserLocation(location);
    }

    public void questionPressed(Question question, int answer){
        presenter.setAnswer(question, answer);
    }

    @Override
    public void showResults(Quiz quiz, int[] playerAnswers, ArrayList<Integer> aiAnswers, long quizTime) {

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
        if (!placedMarkers.contains(question)){
            placedMarkers.add(question);
        }
    }

    @Override
    public void enableMarker(Question question) {
        if (!enabledMarkers.contains(question)){
            enabledMarkers.add(question);
        }
    }

    @Override
    public void removeMarker(Question question) {
        enabledMarkers.remove(question);
        placedHiddenMarkers.remove(question);
        placedMarkers.remove(question);
    }

    @Override
    public void setShowClosestEnabled(boolean value) {

    }

    @Override
    public void setProgress(int current, int total) {

    }

    @Override
    public void initializeAi(QLocation location) {
        bot = location;
    }

    @Override
    public void moveAi(QLocation location) {
        bot = location;
    }
}
