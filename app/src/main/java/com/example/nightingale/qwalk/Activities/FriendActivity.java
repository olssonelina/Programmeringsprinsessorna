package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nightingale.qwalk.Presenter.Friend.IFriend;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Database.Account;
import com.example.nightingale.qwalk.Presenter.Friend.FriendPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Nightingale on 2017-05-16.
 */

public class FriendActivity extends AppCompatActivity implements IFriend {

    private FriendPresenter presenter;

    private EditText UsernameInput;
    private ListView listView;
    private Button addfriendbutton;
    private ProgressBar spinner;


    protected final void onCreate(Bundle savedInstanceState) {
        presenter = new FriendPresenter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend); //ändra namnet till rätt xml-fil
        UsernameInput = (EditText) findViewById(R.id.friendusername);
        listView = (ListView) findViewById(R.id.friendsList);

        addfriendbutton = (Button) findViewById(R.id.addQuizButton);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);


        setListItemsFriends();

    }

    public final void setListItemsFriends() {
        loadList(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteFriend(position);
            }
        });
    }

    public final void onBackPressed(View view) {
        presenter.onBackPressed();
    }

    @Override
    public final void onBackPressed() {
        presenter.onBackPressed();
    }


    private void loadList(AdapterView.OnItemClickListener onItemClickListener) {
        String[] values = new String[Account.getInstance().getFriends().size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = Account.getInstance().getFriends().get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener);
    }

    public final void AddFriendButtonClicked(View view) {
        if (UsernameInput.getText().toString().equals(Account.getInstance().getUsername())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.self_friend_ex),
                    Toast.LENGTH_LONG).show();
        } else {
            addfriendbutton.setEnabled(false);
            listView.setEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            DatabaseHandler.addFriend(UsernameInput.getText().toString());
        }
    }


    public final void DatabaseComplete(String msg) {
        addfriendbutton.setEnabled(true);
        listView.setEnabled(true);
        spinner.setVisibility(View.GONE);
        if (msg.equals("Vän tillagd")) {
            DatabaseHandler.loadFriends();
            setListItemsFriends();
            presenter.menuShouldUpdate();
        }
    }


    private void deleteFriend(int position) {
        presenter.menuShouldUpdate();
        DatabaseHandler.deleteFriend(Account.getInstance().getFriends().get(position));
    }

    @Override
    public void closeWithResult(boolean shouldMenuUpdate) {
        Intent returnIntent = new Intent();
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        returnIntent.putExtra("update", shouldMenuUpdate);
        finish();
    }
}