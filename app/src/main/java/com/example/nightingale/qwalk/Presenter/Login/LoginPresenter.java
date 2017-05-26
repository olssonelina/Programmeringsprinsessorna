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

    public void guestButtonPressed() {
        Account.getInstance().logOut();
        view.openMenu();
    }

    public void registerButtonPressed() {
        view.openRegister();
    }

    public void loginButtonPressed() {
        view.enableButtons(false);
        view.setSpinnerVisible(true);

        try {
            new SendRequest().execute();

        } catch (Exception e) {

        }
    }

    @Override
    public void messageRecieved(String message) {
        view.showText(message);
    }

    private class SendRequest extends AsyncTask<String, Void, String> {

        private String username = view.getUsername();
        private String password = view.getPassword();

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.VALIDATE_URL);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("username", username);
                postDataParams.put("password", password);


                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(DatabaseHandler.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String result) {
            id = result;
            id = id.replaceAll("\\s+", "");
            Log.e("CrashID", id);

            if (id == null || id.equals("<br/>") ) {
                view.showText("Anslutning misslyckades");
            }
            else if (id.equals("Exception:Unabletoresolvehost\"" + DatabaseHandler.HOST + "\":Noaddressassociatedwithhostname")) {
                view.showText("Inget internet");
            }
            else if (Integer.parseInt(id) == -1) {
                view.showText("Fel lösenord/användarnamn!");
            } else if (Integer.parseInt(id) == -2) {
                Account.getInstance().setUserID(Integer.parseInt(id));
                Account.getInstance().setUsername(view.getUsername());
                DatabaseHandler.loadFriends();
                view.openMenu();
            } else {
                Account.getInstance().setUserID(Integer.parseInt(id));
                Account.getInstance().setUsername(view.getUsername());
                DatabaseHandler.loadFriends();
                view.setSpinnerVisible(false);
                view.openMenu();
            }
            view.setSpinnerVisible(false);
            view.enableButtons(true);
        }
    }
}
