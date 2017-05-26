package com.example.nightingale.qwalk;


import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.Presenter.Maps.MapsPresenter;
import com.example.nightingale.qwalk.TestModel.MapsViewTestClass;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kraft on 2017-05-17.
 */

public class MapsPresenterModelTest {

    MapsViewTestClass view;

    @Before
    public void setUpView(){
        view = new MapsViewTestClass();
    }

    @Test
    public void CreateMapsPresenter() {
        Quiz quiz = StandardQuizzes.getChalmersQuiz();
        view.presenter = new MapsPresenter(view, quiz); // Similar to activity started

        view.mapReady(); // Similar to map ready, play quiz


        //view.userPositionUpdated(new QLocation(20, 20)); // Move user somewhere

        assertTrue(view.placedMarkers.size() != 0); //Check that there are questions on the map
        assertTrue(view.enabledMarkers.size() == 0); // Check that no question is in range

        view.userPositionUpdated(quiz.get(0).getLocation()); // Move player to question
        view.userPositionUpdated(quiz.get(0).getLocation()); //

        assertTrue(view.enabledMarkers.size() == 1); // Make sure that we are in range

        view.questionPressed(quiz.get(0), 1); // Answer a question


        assertTrue(view.enabledMarkers.size() == 0);  // Make sure that no question is in range, that the question was removed

        view.userPositionUpdated(quiz.get(1).getLocation());
        view.userPositionUpdated(quiz.get(1).getLocation());

        assertTrue(view.enabledMarkers.size() == 1);





    }
}
