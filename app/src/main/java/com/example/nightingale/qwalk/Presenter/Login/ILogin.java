package com.example.nightingale.qwalk.Presenter.Login;

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

    void setUsername(String username);

    void setPassword(String password);

    void databaseComplete(String message);
}
