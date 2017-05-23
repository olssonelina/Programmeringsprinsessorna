package com.example.nightingale.qwalk.View;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.DatabaseHandler;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Account;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.Model.Tiebreaker;
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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity {

    public static final int CREATE_QUIZ_CODE = 37;
    public static final int ADD_FRIEND_CODE = 14;
    public static final int DELETE_QUIZ_CODE = 15;

    protected int request;
    int offset = 0;
    int userid;
    private Tiebreaker tiebreaker;

    private ListView userList, friendList, featuredList;
    private TextView userListTitle, friendListTitle;

    private List<Quiz> userQuizzes = new ArrayList<>();
    private List<Quiz> friendQuizzes = new ArrayList<>();
    private List<Quiz> featuredQuizzes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userList = (ListView) findViewById(R.id.userList);
        friendList = (ListView) findViewById(R.id.friendsList);
        featuredList = (ListView) findViewById(R.id.standardList);

        userListTitle = (TextView) findViewById(R.id.userListText);
        friendListTitle = (TextView) findViewById(R.id.friendsListText);
        friendListTitle.setVisibility(friendQuizzes.size() == 0 ? View.INVISIBLE : View.VISIBLE);

        loadFeaturedQuizzes();
        if (!(Account.getInstance().getUserID() == -1)) {
            loadOnlineQuizzes(Account.getInstance().getUserID(), userQuizzes);
            loadUserList();
            loadFriendQuizzes();
            loadFriendsList();


        }


        loadFeaturedList();

    }

    private void loadFeaturedQuizzes() {
        featuredQuizzes.add(StandardQuizzes.getMachineStudyRoomsQuiz());
        featuredQuizzes.add(StandardQuizzes.getChalmersQuiz());
        featuredQuizzes.add(StandardQuizzes.getAdressQuiz());
    }

    private void loadOnlineQuizzes(int UserID, List<Quiz> currentQuizList) {
        if (currentQuizList == userQuizzes) {
            Log.d("List", "Using user list");
        } else if (currentQuizList == friendQuizzes) {
            Log.d("List", "Using friend list");
        }
        userid = UserID;
        Log.d("JSON", "Method entered");
        request = 0;
        int quizAmount = 0;
        try {
            Log.d("JSON", "Trying");
            String JSONstring = new SendRequest().execute().get();
            Log.d("JSON", JSONstring);
            JSONstring = JSONstring.replaceAll("\\s+", "");
            int RequestAmount = Integer.parseInt(JSONstring);
            Log.d("JSON", String.valueOf(RequestAmount));
            quizAmount = RequestAmount;
            Log.d("JSON", String.valueOf(quizAmount));
        } catch (Exception e) {

            Log.d("JSON", "Crash");
            Log.d("JSON", e.getMessage());
        }
        Log.d("JSON", "Continuing");

        String title  = "", description  = "";
        int quizID = 0;

        if (!(quizAmount == 0)) {
            request = 1;
            for (int i = 0; i < quizAmount; i++) {
                offset = i;
                try {
                    String JSONstring = new SendRequest().execute().get();

                    Log.d("JSON", JSONstring);
                    JSONArray jsonArray = new JSONArray(JSONstring);

                    List<Question> questions = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); ++j) {
                        if (j == 0) {
                            JSONObject quiz = jsonArray.getJSONObject(j);
                            title = quiz.getString("title");
                            Log.d("JSON", title);
                            description = quiz.getString("description");
                            Log.d("JSON", description);
                            quizID = quiz.getInt("quizid");
                            Log.d("JSON", String.valueOf(quizID));
                        } else {

                            ArrayList<String> options= new ArrayList<>();

                            JSONObject question = jsonArray.getJSONObject(j);
                            String questionDescription = question.getString("description");
                            Log.d("JSON", questionDescription);
                            options.add(question.getString("option1"));
                            Log.d("JSON", options.get(0));
                            options.add(question.getString("option2"));
                            Log.d("JSON", options.get(1));
                            int correctanswer = question.getInt("correctanswer");
                            Log.d("JSON", String.valueOf(correctanswer));
                            double latitude = question.getDouble("latitude");
                            Log.d("JSON", String.valueOf(latitude));
                            double longitude = question.getDouble("longitude");
                            Log.d("JSON", String.valueOf(longitude));

                            int questiontype = question.getInt("questiontype");
                            if (questiontype == 0) {
                                options.add(question.getString("option3"));
                                Log.d("JSON", options.get(2));
                                options.add(question.getString("option4"));
                                Log.d("JSON", options.get(3));
                                questions.add(new OptionQuestion(questionDescription, options, correctanswer, latitude, longitude));
                                Log.d("JSON", "Question added");
                            } else if (questiontype == 1) {
                                tiebreaker = new Tiebreaker(questionDescription, correctanswer, latitude, longitude, Integer.parseInt(options.get(0)), Integer.parseInt(options.get(1)));
                                Log.d("JSON", "Tiebreaker Set");
                            }

                        }


                        // ...
                    }
                    if (!(tiebreaker == null)) {
                        questions.add(tiebreaker);
                    }
                    Log.d("JSON", "Setting questions");
                    Quiz q = new Quiz(title, description, quizID, questions);
                    Log.d("JSON", "Questions Set");
                    currentQuizList.add(q);
/*
                    Quiz q = new Quiz("Gissa huset!","Besök skaparna av appen och gissa vem som bor var!");
                    List<OptionQuestion> questions = new ArrayList<>();
                    questions.add(new OptionQuestion("Vem bor så här nära Chalmers?", "Katten", "Pil", "Nightinggale", "Elit", 1,57.689280, 11.972306));
                    //questions.get(0).setLocation(57.689280, 11.972306);
                    questions.add(new OptionQuestion("Vem kan bo här?", "Pil", "Katten", "Nightinggale", "Elit", 2,57.742081, 11.969506));
                    //questions.get(1).setLocation(57.742081, 11.969506);
                    questions.add(new OptionQuestion("Vem bor inneboende här?", "Pil", "Nightinggale", "Elit", "Katten", 3,57.735626, 12.116774));
                    //questions.get(2).setLocation(57.735626, 12.116774);
                    questions.add(new OptionQuestion("Vem orkar pendla från Kungsbacka?", "Elit", "Pil", "Nightinggale", "Katten", 0,57.543822, 12.103735));
                    //questions.get(3).setLocation(57.543822, 12.103735);
                    q.setQuestions(questions);
*/
                } catch (Exception e) {
                    Log.d("JSON", "Crash2");
                }
            }


        }
    }

    private void loadFriendQuizzes() {
        int len = Account.getInstance().getFriendIDs().size();
        if (len > 0) {
            for (int i = 1; i < len; i++) {
                loadOnlineQuizzes(Account.getInstance().getFriendIDs().get(i), friendQuizzes);
                offset = 0;
            }
        }
    }

    public void loadHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void loadFriends(View view) {
        if (Account.getInstance().getUserID() == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_login), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
            toast.show();
        } else {
            Intent intent = new Intent(this, FriendActivity.class);
            startActivityForResult(intent, ADD_FRIEND_CODE);
        }

    }

    private void loadUserList() {
        userListTitle.setVisibility(userQuizzes.size() == 0 ? View.INVISIBLE : View.VISIBLE);
        String userTitleText = Account.getInstance().getUsername();
        if (userTitleText.charAt(userTitleText.length()-1) != 's'){
            userTitleText += "s";
        }
        userTitleText += " Qwalks";
        userListTitle.setText(userTitleText);
        if (userQuizzes.size() == 0) {
            return;
        }

        String[] values = new String[userQuizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = userQuizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        userList.setAdapter(adapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position, userQuizzes);
            }
        });

        setListViewHeightBasedOnItems(userList);

    }

    private void loadFriendsList() {

        friendListTitle.setVisibility(friendQuizzes.size() == 0 ? View.INVISIBLE : View.VISIBLE);
        if (friendQuizzes.size() == 0) {
            return;
        }

        String[] values = new String[friendQuizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = friendQuizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        friendList.setAdapter(adapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position, friendQuizzes);
            }
        });

        setListViewHeightBasedOnItems(friendList);


    }

    private void loadFeaturedList() {
        String[] values = new String[featuredQuizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = featuredQuizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        featuredList.setAdapter(adapter);

        featuredList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position, featuredQuizzes);
            }
        });

        setListViewHeightBasedOnItems(featuredList);


    }


    private void showDetails(Quiz quiz) {
        Intent intent = new Intent(this, QuizDetailsActivity.class);
        intent.putExtra("quiz", quiz);
        intent.putExtra("editable", true); //TODO ska inte alltid vara editable
        startActivityForResult(intent, DELETE_QUIZ_CODE);
    }

    private void showDetails(int index, List<Quiz> quiz) {
        showDetails(quiz.get(index));
    }

    public void createButtonPressed(View view) {
        if (Account.getInstance().getUserID() == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_ex), Toast.LENGTH_LONG); //Översatte "Please Log In" till svenska
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
            toast.show();
        } else {
            Intent intent = new Intent(this, CreateQuizActivity.class);
            startActivityForResult(intent, CREATE_QUIZ_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_QUIZ_CODE || requestCode == DELETE_QUIZ_CODE) {
            userQuizzes.clear();
            loadOnlineQuizzes(Account.getInstance().getUserID(), userQuizzes);
            loadUserList();
        }
        else if (requestCode == ADD_FRIEND_CODE){
            friendQuizzes.clear();
            loadFriendQuizzes();
            loadFriendsList();
        }
    }


    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.getReadQuizURL());

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("request", request);
                postDataParams.put("offset", offset);
                postDataParams.put("userid", userid);


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
        }
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
