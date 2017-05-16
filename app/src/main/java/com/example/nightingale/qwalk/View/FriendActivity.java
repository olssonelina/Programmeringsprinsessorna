package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Account;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nightingale on 2017-05-16.
 */

public class FriendActivity extends AppCompatActivity{

    EditText UsernameInput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend); //ändra namnet till rätt xml-fil
        UsernameInput  = (EditText)findViewById(R.id.friendusername);


        //loadQuizzes();

        //loadList();
    }

    public void AddFriendButtonClicked(View view) {
        if(UsernameInput.getText().toString().equals(Account.getInstance().getUsername())){
            Toast.makeText(getApplicationContext(), "Du kan inte lägga till dig själv som vän",
                    Toast.LENGTH_LONG).show();
        }
        else{


        try {
            String ID = new SendRequest().execute().get();
            Log.e("response",ID);
            ID = ID.replaceAll("\\s+", "");

            String msg = "";

            if(ID.equals("0")){
                msg = "Error";
            }
            else if(ID.equals("1")){
                msg = "Användarnamnet finns inte";
            }
            else if(ID.equals("2")){
                msg = "Du är redan vän med den personen";
            }
            else if(ID.equals("3")){
                msg = "Vän tillagd";
            }


            Toast.makeText(getApplicationContext(), msg,
                        Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
        }




    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String Username = UsernameInput.getText().toString();
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(DatabaseHandler.getInsertFriendURL());

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("friendusername", Username);
                postDataParams.put("userid", Account.getInstance().getUserID());
                postDataParams.put("request", 0);



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
        }
    }



}
