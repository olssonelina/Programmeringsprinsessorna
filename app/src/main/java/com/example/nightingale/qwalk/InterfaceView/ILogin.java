package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface ILogin {
    void setSpinnerVisible(boolean value);
    void openMenu();
    void openRegister();
    void enableButtons(boolean value);
    String getUsername();
    String getPassword();
    void showText(String message);
}
