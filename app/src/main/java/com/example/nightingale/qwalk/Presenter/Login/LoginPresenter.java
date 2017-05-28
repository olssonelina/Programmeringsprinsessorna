package com.example.nightingale.qwalk.Presenter.Login;

import com.example.nightingale.qwalk.Model.Database.Account;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class LoginPresenter implements IOnMessageRecievedListener {

    private ILogin view;

    public LoginPresenter(ILogin view) {
        this.view = view;
        view.setSpinnerVisible(false);
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    public final void guestButtonPressed() {
        Account.getInstance().logOut();
        view.openMenu();
        view.setPassword("");
        view.setUsername("");
    }

    public final void registerButtonPressed() {
        view.openRegister();
    }

    public final void loginButtonPressed() {
        view.enableButtons(false);
        view.setSpinnerVisible(true);

        DatabaseHandler.login(view.getUsername(), view.getPassword());
    }

    @Override
    public final void messageRecieved(String message) {
        view.databaseComplete(message);
    }


    public final void onRegisterResult(String username, String password){
        view.setUsername(username);
        view.setPassword(password);
        loginButtonPressed();
    }
}
