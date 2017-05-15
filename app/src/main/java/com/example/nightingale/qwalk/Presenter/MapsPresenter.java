package com.example.nightingale.qwalk.Presenter;

import android.location.Location;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.QwalkGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-05-12.
 */

public class MapsPresenter {
    private IMaps view;
    private QwalkGame model;
    private QLocation user;

    /**
     *
     * @param view
     * @param quiz
     */
    public MapsPresenter(IMaps view, Quiz quiz) {
        this.view = view;
        this.model = new QwalkGame(this, quiz);
    }

    /**
     *
     * @param l
     * @param visible
     */
    public void placeMarker(QLocation l, boolean visible){

    }

    /**
     *
     * @param location
     */
    public void updateUserLocation(QLocation location){
        model.setUserLocation(location);
    }




}
