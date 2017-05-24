package com.example.nightingale.qwalk.View;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button registerbutton;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = (EditText) findViewById(R.id.usernameRegister);
        passwordInput = (EditText) findViewById(R.id.passwordRegister);
        confirmPasswordInput = (EditText) findViewById(R.id.confirmPasswordRegister);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        spinner.setVisibility(View.GONE);

    }

    public void registerButtonClicked(View view) {
        registerbutton.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);
        if (passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            new SendRequest().execute();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_no_match), Toast.LENGTH_SHORT).show(); // "Passwords don't match" ->
        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.INSERT_ACCOUNT_URL);

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
            spinner.setVisibility(View.GONE);
            registerbutton.setEnabled(true);
            result = result.replaceAll("\\s+", "");

            if (result.equals("Exception:Unabletoresolvehost\"" + DatabaseHandler.HOST + "\":Noaddressassociatedwithhostname")) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_ex),
                        Toast.LENGTH_LONG).show();
            } else if (result == null) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_failed_ex),
                        Toast.LENGTH_LONG).show(); //"Connection Failed" -> "Uppkoppnilng misslyckades"
            } else if (result.equals("0")) {
                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.done),
                //Toast.LENGTH_LONG).show(); // "Success" -> "Klar"
                finish();

            } else if (result.equals("1")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.username_taken),
                        Toast.LENGTH_LONG).show();

            } else if (result.equals("2")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.username_empty),
                        Toast.LENGTH_LONG).show();

            } else if (result.equals("3")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty),
                        Toast.LENGTH_LONG).show();

            }


        }
    }


}
