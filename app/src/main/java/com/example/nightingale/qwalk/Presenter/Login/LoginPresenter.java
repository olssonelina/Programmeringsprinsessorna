package com.example.nightingale.qwalk.Presenter.Login;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nightingale.qwalk.Model.Database.Account;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class LoginPresenter implements IOnMessageRecievedListener {

    private ILogin view;
    private String id;

    public LoginPresenter(ILogin view) {
        this.view = view;
        view.setSpinnerVisible(false);
        DatabaseHandler.setOnMessageRecievedListener(this);
    }

    public final void guestButtonPressed() {
        Account.getInstance().logOut();
        view.openMenu();
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
        view.DatabaseComplete(message);
    }

}
