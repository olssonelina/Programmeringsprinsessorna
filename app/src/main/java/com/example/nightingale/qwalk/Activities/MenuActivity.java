package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nightingale.qwalk.Presenter.Menu.IMenu;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Presenter.Menu.MenuPresenter;
import com.example.nightingale.qwalk.R;

import java.util.List;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity implements IMenu {

    private MenuPresenter presenter;

    public static final int CREATE_QUIZ_CODE = 37;
    public static final int ADD_FRIEND_CODE = 14;
    public static final int SHOW_DETAILS_CODE = 15;

    private ListView userList, friendList, defaultList;
    private TextView userListTitle, friendListTitle, defaultTitle;
    private Button createQuizButton, friendsButton;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userList = (ListView) findViewById(R.id.userList);
        friendList = (ListView) findViewById(R.id.friendsList);
        defaultList = (ListView) findViewById(R.id.standardList);
        defaultTitle = (TextView) findViewById(R.id.standardListText);
        friendsButton = (Button) findViewById(R.id.friends);
        userListTitle = (TextView) findViewById(R.id.userListText);
        friendListTitle = (TextView) findViewById(R.id.friendsListText);
        createQuizButton = (Button) findViewById(R.id.addQuizButton);

        presenter = new MenuPresenter(this);
    }

    @Override
    public final  void openCreateNewQuiz() {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivityForResult(intent, CREATE_QUIZ_CODE);
    }

    @Override
    public final void openFriend() {
        Intent intent = new Intent(this, FriendActivity.class);
        startActivityForResult(intent, ADD_FRIEND_CODE);
    }

    @Override
    public final void openQuizDetails(Quiz quiz, boolean editable) {
        Intent intent = new Intent(this, QuizDetailsActivity.class);
        intent.putExtra("quiz", quiz);
        intent.putExtra("editable", editable);
        startActivityForResult(intent, SHOW_DETAILS_CODE);
    }


    @Override
    public final void setListItemsUser(List<Quiz> userQuizzes) {
        loadList(userList, userQuizzes, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.userQuizPressed(position);
            }
        });
    }

    @Override
    public final void setListItemsFriends(List<Quiz> friendsQuizzes) {
        loadList(friendList, friendsQuizzes, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.friendQuizPressed(position);
            }
        });
    }

    @Override
    public final void setListItemsDefault(List<Quiz> defaultQuizzes) {
        loadList(defaultList, defaultQuizzes, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.defaultQuizPressed(position);
            }
        });
    }

    @Override
    public final void setListTitleUserVisible(boolean value) {
        userListTitle.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public final void setListTitleFriendsVisible(boolean value) {
        friendListTitle.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public final void setListTitleDefaultVisible(boolean value) {
        defaultTitle.setEnabled(value);
    }

    @Override
    public final void setAccountFunctionalityEnabled(boolean value) {
        friendsButton.setEnabled(value);
        friendsButton.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
        createQuizButton.setEnabled(value);
    }

    @Override
    public final void setListTitleUserText(String text) {
        userListTitle.setText(text);
    }

    public final void friendsPressed(View view) {
        presenter.friendsButtonPressed();
    }

    public final void createButtonPressed(View view) {
        presenter.createQuizPressed();
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data.getBooleanExtra("update", false)) {
                switch (requestCode) {
                    default: // Create Quiz & Show Details
                        presenter.userQuizUpdated();
                        break;
                    case ADD_FRIEND_CODE:
                        presenter.friendQuizUpdated();
                        break;
                }
            }
        } catch (NullPointerException e) {

        }
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void loadList(ListView listView, List<Quiz> quizzes, AdapterView.OnItemClickListener onItemClickListener) {

        String[] values = new String[quizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = quizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener);

        setListViewHeightBasedOnItems(listView);

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }
}