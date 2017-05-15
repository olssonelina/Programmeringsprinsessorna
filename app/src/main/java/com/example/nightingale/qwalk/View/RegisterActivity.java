package com.example.nightingale.qwalk.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog progress;


    EditText UsernameInput;
    EditText PasswordInput;
    EditText ConfirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UsernameInput   = (EditText)findViewById(R.id.usernameRegister);
        PasswordInput   = (EditText)findViewById(R.id.passwordRegister);
        ConfirmPasswordInput   = (EditText)findViewById(R.id.confirmPasswordRegister);

    }

    public void SendButtonClicked(View view) {

        if(PasswordInput.getText().toString().equals(ConfirmPasswordInput.getText().toString())) {
            new SendRequest().execute();
        }
        else{
            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String Username = UsernameInput.getText().toString();
        String Password = PasswordInput.getText().toString();
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(DatabaseHandler.getInsertAccountURL());

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("username", Username);
                postDataParams.put("password", Password);


                Log.e("params",postDataParams.toString());

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

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String result) {
            result= result.replaceAll("\\s+","");
if(result.equals("0")) {
    Toast.makeText(getApplicationContext(), "Success",
            Toast.LENGTH_LONG).show();

}
else if(result.equals("1")){
    Toast.makeText(getApplicationContext(), "Username Taken",
            Toast.LENGTH_LONG).show();

}
else if(result.equals("2")){
    Toast.makeText(getApplicationContext(), "Username Empty",
            Toast.LENGTH_LONG).show();

}
else if(result.equals("3")){
    Toast.makeText(getApplicationContext(), "Password Empty",
            Toast.LENGTH_LONG).show();

}



        }
    }


}
