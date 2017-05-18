package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity {

    public static final int CREATE_QUIZ_CODE = 37;

    protected int request;
    int offset = 0;
    int userid;
    private Tiebreaker tiebreaker;

    private ListView listView;

    private List<Quiz> quizzes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); //ändra namnet till rätt xml-fil
        loadQuizzes();
        if(!(Account.getInstance().getUserID() == -1)){
            loadOnlineQuizzes(Account.getInstance().getUserID());
            loadFriendQuizzes();
        }
        loadList();
    }

    private void loadQuizzes() {
        //quizzes.add(StandardQuizzes.getMachineStudyRoomsQuiz());
        quizzes.add(StandardQuizzes.getChalmersQuiz());
        quizzes.add(StandardQuizzes.getHiddenChalmersQuiz());
        quizzes.add(StandardQuizzes.getAllChalmersQuiz());

        //quizzes.add(StandardQuizzes.getAdressQuiz());
    }

    private void loadOnlineQuizzes(int UserID) {
        userid = UserID;
        Log.d("JSON", "Method entered");
        request = 0;
        int quizAmount = 0;
        try {
            Log.d("JSON", "Trying");
            String JSONstring = new SendRequest().execute().get();
            Log.d("JSON", JSONstring);
            JSONstring = JSONstring.replaceAll("\\s+","");
            int RequestAmount = Integer.parseInt(JSONstring);
                    Log.d("JSON", String.valueOf(RequestAmount));
            quizAmount = RequestAmount;
            Log.d("JSON", String.valueOf(quizAmount));
        } catch (Exception e) {

            Log.d("JSON", "Crash");
            Log.d("JSON", e.getMessage());
        }
        Log.d("JSON", "Continuing");
        if(!(quizAmount == 0)){
            request = 1;
            for(int i = 0; i < quizAmount; i++){
                offset = i;
                try {
                    String JSONstring = new SendRequest().execute().get();

                    Log.d("JSON", JSONstring);
                    JSONArray jsonArray = new JSONArray(JSONstring);
                    Quiz q = new Quiz("","");
                    List<Question> questions = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); ++j) {
                        if(j == 0){
                            JSONObject quiz = jsonArray.getJSONObject(j);
                            String title = quiz.getString("title");
                            Log.d("JSON", title);
                            String description = quiz.getString("description");
                            Log.d("JSON", description);
                            q = new Quiz(title,description);
                        }
                        else{

                            JSONObject question = jsonArray.getJSONObject(j);
                            String description = question.getString("description");
                            Log.d("JSON", description);
                            String option1 = question.getString("option1");
                            Log.d("JSON", option1);
                            String option2 = question.getString("option2");
                            Log.d("JSON", option2);
                            int correctanswer = question.getInt("correctanswer");
                            Log.d("JSON", String.valueOf(correctanswer));
                            double latitude = question.getDouble("latitude");
                            Log.d("JSON", String.valueOf(latitude));
                            double longitude = question.getDouble("longitude");
                            Log.d("JSON", String.valueOf(longitude));

                            int questiontype = question.getInt("questiontype");
                            if(questiontype == 0){
                            String option3 = question.getString("option3");
                            Log.d("JSON", option3);
                            String option4 = question.getString("option4");
                            Log.d("JSON", option4);
                            questions.add(new OptionQuestion(description, option1, option2, option3, option4, correctanswer, latitude, longitude));
                            Log.d("JSON", "Question added");

                            };
                            if(questiontype == 1){

                                tiebreaker = new Tiebreaker(description, correctanswer, latitude, longitude, Integer.parseInt(option1), Integer.parseInt(option2));
                                Log.d("JSON", "Tiebreaker Set");
                            }

                        }


                        // ...
                    }
                    if (!(tiebreaker == null)){
                        questions.add(tiebreaker);
                    }
                    Log.d("JSON", "Setting questions");
                    q.setQuestions(questions);
                    Log.d("JSON", "Questions Set");
                    quizzes.add(q);
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

    private void loadFriendQuizzes(){
        int len = Account.getInstance().getFriendIDs().size();
        if(len > 0) {
            for (int i = 1; i < len; i++) {
                loadOnlineQuizzes(Account.getInstance().getFriendIDs().get(i));
            }
        }
    }

    public void loadHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void loadFriends(View view) {
        if(Account.getInstance().getUserID() == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_login), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
            toast.show();
        }
        else{
            Intent intent = new Intent(this, FriendActivity.class);
            startActivity(intent);
        }

    }

    public void loadSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void loadList() {
        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[quizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = quizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play(position);
            }
        });
    }

    private void play(Quiz quiz) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }

    private void play(int index) {
        play(quizzes.get(index));
    }

    public void createButtonPressed(View view) {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivityForResult(intent, CREATE_QUIZ_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_QUIZ_CODE) {

            loadList();

            //TODO ladda upp nya quizzen här!
        }
    }






    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(DatabaseHandler.getReadQuizURL());

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("request", request);
                postDataParams.put("offset", offset);
                postDataParams.put("userid", userid);



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
