package com.example.nightingale.qwalk.Presenter.Friend;

import com.example.nightingale.qwalk.Model.Database.Account;
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

        view.setSpinnerVisibility(false);
        view.setFriendList(getFriendsNames());
    }

    public final void onBackPressed(){
        view.closeWithResult(shouldMenuUpdate);
    }

    @Override
    public final void messageRecieved(String message) {
        view.setAddFriendButtonEnabled(true);
        view.setFriendListEnabled(true);
        view.setSpinnerVisibility(false);
        if (message.equals("Vän tillagd")) {
            DatabaseHandler.loadFriends();
            view.setFriendList(getFriendsNames());
            shouldMenuUpdate = true;
        }
    }

    public final void addFriendButtonPressed(){
        if (view.getUsername().equals(Account.getInstance().getUsername())) {
            view.showError("Du kan inte lägga till dig själv!");
        } else {
            view.setSpinnerVisibility(true);
            view.setAddFriendButtonEnabled(false);
            view.setFriendListEnabled(false);
            DatabaseHandler.addFriend(view.getUsername());
        }
    }

    public final void deleteFriend(int index){
        view.setSpinnerVisibility(true);
        view.setAddFriendButtonEnabled(false);
        view.setFriendListEnabled(false);
        DatabaseHandler.deleteFriend(Account.getInstance().getFriends().get(index));
        shouldMenuUpdate = true;
        view.setFriendList(getFriendsNames());
    }

    public String[] getFriendsNames(){
        String[] friends = new String[Account.getInstance().getFriends().size()];
        for (int i = 0; i < friends.length; i++) {
            friends[i] = Account.getInstance().getFriends().get(i);
        }
        return friends;
    }
}
