package com.example.nightingale.qwalk.Presenter.Friend;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface IFriend {

    void closeWithResult(boolean shouldMenuUpdate);

    void setFriendList(String[] friends);

    void setSpinnerVisibility(Boolean value);

    void setAddFriendButtonEnabled(Boolean value);

    void setFriendListEnabled(Boolean value);

    String getUsername();

    void showError(String message);
}
