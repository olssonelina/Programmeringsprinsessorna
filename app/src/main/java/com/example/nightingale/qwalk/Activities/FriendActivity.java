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
import com.example.nightingale.qwalk.Presenter.Friend.FriendPresenter;
import com.example.nightingale.qwalk.R;

/**
 * Created by Nightingale on 2017-05-16.
 */

public class FriendActivity extends AppCompatActivity implements IFriend {

    private FriendPresenter presenter;

    private EditText usernameInput;
    private ListView friendList;
    private Button addFriendButton;
    private ProgressBar spinner;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        usernameInput = (EditText) findViewById(R.id.friendusername);
        friendList = (ListView) findViewById(R.id.friendsList);
        addFriendButton = (Button) findViewById(R.id.addQuizButton);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        presenter = new FriendPresenter(this);
    }

    @Override
    public final void setFriendList(String[] friends) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, friends);

        friendList.setAdapter(adapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.deleteFriend(position);
            }
        });
    }

    @Override
    public final void setSpinnerVisibility(Boolean value) {
        spinner.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void setAddFriendButtonEnabled(Boolean value) {
        addFriendButton.setEnabled(value);
    }

    @Override
    public final void setFriendListEnabled(Boolean value) {
        friendList.setEnabled(value);
    }

    @Override
    public final String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public final void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public final void onBackPressed(View view) {
        presenter.onBackPressed();

    }

    @Override
    public final void onBackPressed() {
        presenter.onBackPressed();
    }

    public final void addFriendButtonClicked(View view) {
        presenter.addFriendButtonPressed();
    }

    @Override
    public final void closeWithResult(boolean shouldMenuUpdate) {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        returnIntent.putExtra("update", shouldMenuUpdate);
        finish();
    }
}