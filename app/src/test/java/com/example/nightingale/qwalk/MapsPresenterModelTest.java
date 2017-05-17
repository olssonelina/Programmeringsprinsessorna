package com.example.nightingale.qwalk;


import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.Actor;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.Presenter.MapsPresenter;
import com.example.nightingale.qwalk.TestModel.MapsViewTestClass;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        view.presenter = new MapsPresenter(view, StandardQuizzes.getChalmersQuiz());

        view.mapReady();

        view.userPositionUpdated(new QLocation(50, 50));


        assertTrue(view.placedMarkers.size() != 0);


    }
}
