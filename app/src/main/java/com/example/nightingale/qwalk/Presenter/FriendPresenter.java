package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IFriend;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class FriendPresenter implements IOnMessageRecievedListener{

    IFriend view;

    public FriendPresenter(IFriend view) {
        this.view = view;
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    @Override
    public void messageRecieved(String message) {
        view.DatabaseComplete(message);
    }
}
