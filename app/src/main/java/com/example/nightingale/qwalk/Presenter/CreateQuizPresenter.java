package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.ICreateQuiz;
import com.example.nightingale.qwalk.InterfaceView.ILogin;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class CreateQuizPresenter implements IOnMessageRecievedListener {

    private ICreateQuiz view;

    public CreateQuizPresenter(ICreateQuiz view) {
        this.view = view;
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    @Override
    public void messageRecieved(String message) {
        view.DatabaseComplete(message);
    }
}
