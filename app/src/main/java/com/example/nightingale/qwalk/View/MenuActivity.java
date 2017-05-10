package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.User;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.R;

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

    private ListView listView;

    private List<Quiz> quizzes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); //ändra namnet till rätt xml-fil
        loadQuizzes();
        if(!(User.getInstance().getUserID() == -1)){
            loadOnlineQuizzes();
        }
        loadList();
    }

    private void loadQuizzes() {
        quizzes.add(StandardQuizzes.getMachineStudyRoomsQuiz());
        quizzes.add(StandardQuizzes.getChalmersQuiz());
        quizzes.add(StandardQuizzes.getAdressQuiz());
        //TODO Kevin, här kanske du kan lägga till från databasen ?. Eventuellt fler standardquizzes med
    }

    private void loadOnlineQuizzes() {
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

                } catch (Exception e) {

                }
            }



        }

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

            Quiz newQuiz = (Quiz)data.getParcelableExtra("quiz");
            quizzes.add(newQuiz);
            loadList();

            //TODO ladda upp nya quizzen här!
        }
    }






    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("https://programmeringsprinsessorna.000webhostapp.com/readquiz.php");

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("request", request);
                postDataParams.put("offset", offset);
                postDataParams.put("userid", User.getInstance().getUserID());



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
                writer.write(getPostDataString(postDataParams));

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

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
