package com.example.nightingale.qwalk.Presenter.Friend;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class FriendPresenter implements IOnMessageRecievedListener{

    private IFriend view;
    private boolean shouldMenuUpdate = false;

    public FriendPresenter(IFriend view) {
        this.view = view;
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    public void menuShouldUpdate(){
        shouldMenuUpdate = true;
    }

    public void onBackPressed(){
        view.closeWithResult(shouldMenuUpdate);
    }

    @Override
    public final void messageRecieved(String message) {
        view.DatabaseComplete(message);
    }
}
