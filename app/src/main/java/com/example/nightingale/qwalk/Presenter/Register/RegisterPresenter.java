package com.example.nightingale.qwalk.Presenter.Register;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;
import com.example.nightingale.qwalk.Presenter.CreateQuiz.ICreateQuiz;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class RegisterPresenter implements IOnMessageRecievedListener {

    private IRegister view;

    public RegisterPresenter(IRegister view) {
        this.view = view;
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    @Override
    public final void messageRecieved(String message) {
        view.DatabaseComplete(message);
    }
}