package com.example.nightingale.qwalk.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.IAnswerOption;
import com.example.nightingale.qwalk.InterfaceView.IFriendActivity;
import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Account;
import com.example.nightingale.qwalk.R;

/**
 * Created by Nightingale on 2017-05-16.
 */

public class FriendActivity extends AppCompatActivity implements IFriendActivity {

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
        listView = (ListView) findViewById(R.id.friendsList);
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

           addfriendbutton.setEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            DatabaseHandler.addFriend(UsernameInput.getText().toString(), this);


        }



    }

    public void AddFriendComplete(String msg){

        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();

        addfriendbutton.setEnabled(true);
        spinner.setVisibility(View.GONE);
        DatabaseHandler.loadFriends();
        loadList();
    }







}
