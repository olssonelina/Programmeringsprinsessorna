package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private ListView listView;
    Button addfriendbutton;
    private ProgressBar spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend); //ändra namnet till rätt xml-fil
        UsernameInput  = (EditText)findViewById(R.id.friendusername);

        addfriendbutton = (Button) findViewById(R.id.addfriendbutton);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        loadList();
        //loadQuizzes();

        //loadList();
    }


    private void loadList() {
        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[Account.getInstance().getFriends().size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = Account.getInstance().getFriends().get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
    }

    public void AddFriendButtonClicked(View view) {
        if (UsernameInput.getText().toString().equals(Account.getInstance().getUsername())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.self_friend_ex),
                    Toast.LENGTH_LONG).show();
        }
        else {

           // addfriendbutton.setEnabled(false);
            //spinner.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), DatabaseHandler.addFriend(UsernameInput.getText().toString()),
                    Toast.LENGTH_LONG).show();
            DatabaseHandler.loadFriends();
            loadList();
        }

    }







}
