package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.Model.User;
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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kraft on 2017-04-27.
 */

public class CreateQuizActivity extends AppCompatActivity {
    public String test;
    ArrayList<Integer> QuestionIDArray = new ArrayList<Integer>();
    String JSONarrayString;
    private int counter = 0;
    private int readycheck = 0;
    EditText quizTitle;
    EditText quizDescription;
    ArrayList<Question> questions = new ArrayList<>();

    public final static int OPTIONQUESTION_CODE = 7;
    public final static int TIEBREAKER_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);

        quizTitle = (EditText) findViewById(R.id.quizTitleField);
        quizDescription = (EditText) findViewById(R.id.descriptionField);

    }

    public void addQuestionButtonClicked(View view) {
        Intent intent = new Intent(this, CreateQuestionActivity.class);
        startActivityForResult(intent, OPTIONQUESTION_CODE);
    }

    public void addTiebreaker(View view) {
        Intent intent = new Intent(this, CreateTiebreakerActivity.class);
        startActivityForResult(intent, TIEBREAKER_CODE);
    }

    public void createQuiz(View view) {
        if (!isQuizComplete()) {
            sendErrorMsg();
        } else {
            try {
                saveQuiz();
            } catch (Exception e) {

            }
        }
    }

    public boolean isQuizComplete() {
        if (quizTitle.getText().toString().equals("") || quizDescription.getText().toString().equals("") || questions.size() == 0) {
            return false;
        } else if (OptionQuestion.getQuestionsToSend().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void sendErrorMsg() {
        String msg;
        if (quizTitle.getText().toString().equals("")) {
            msg = "Fyll i titel";
        } else if (quizDescription.getText().toString().equals("")) {
            msg = "Fyll i beskrivning";
        } else if (OptionQuestion.getQuestionsToSend().size() == 0) {
            msg = "Lägg till minst en fråga";
        } else if (quizDescription.getText().toString().equals("")) {
            msg = "Fyll i beskrivning";
        } else {
            msg = "Error";
        }
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
        toast.show();
    }

    public void saveQuiz() throws InterruptedException {
        if (User.getInstance().getUserID() == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Log In", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
            toast.show();

        } else {
            Quiz quiz = new Quiz(quizTitle.getText().toString(), quizDescription.getText().toString());
            quiz.setQuestions(questions);


            QuestionIDArray = new ArrayList<Integer>();
            counter = 0;

            ArrayList<OptionQuestion> questionToSend = OptionQuestion.getQuestionsToSend();

            for (int i = 0; i < questionToSend.size(); i++) {
                try {

                    test = new SendRequest().execute().get();
                    Log.d("Getcomplete", "True");
                    Log.d("Getcomplete", test);
                    test = test.replaceAll("\\s+", "");
                    QuestionIDArray.add(Integer.parseInt(test));
                } catch (Exception e) {
                    Log.d("Getcomplete", "False");
                    break;
                }
                Log.d("Getcomplete", "Test");
            }

            Log.d("JSONindex", String.valueOf(QuestionIDArray.get(0)));
            JSONArray jsArray = new JSONArray(QuestionIDArray);
            JSONarrayString = jsArray.toString();
            Log.d("JSON", JSONarrayString);
            readycheck = 1;
            counter = 0;
            new SendRequest().execute();


            Intent returnIntent = new Intent();
            returnIntent.putExtra("quiz", quiz);
            setResult(GetPositionActivity.RESULT_OK, returnIntent);
            finish();

            OptionQuestion.wipeQuestionsToSend();

        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        String title = quizTitle.getText().toString();
        String description = quizDescription.getText().toString();

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {


                URL url = new URL("https://programmeringsprinsessorna.000webhostapp.com/insertquiz.php");

                JSONObject postDataParams = new JSONObject();


                ArrayList<OptionQuestion> questionToSend = OptionQuestion.getQuestionsToSend();


                Log.d("VARIABLE", questionToSend.get(counter).getQuestionTitle());
                postDataParams.put("description", questionToSend.get(counter).getQuestionTitle());

                Log.d("VARIABLE", questionToSend.get(counter).getOption1());
                postDataParams.put("option1", questionToSend.get(counter).getOption1());

                Log.d("VARIABLE", questionToSend.get(counter).getOption2());
                postDataParams.put("option2", questionToSend.get(counter).getOption2());

                Log.d("VARIABLE", questionToSend.get(counter).getOption3());
                postDataParams.put("option3", questionToSend.get(counter).getOption3());

                Log.d("VARIABLE", questionToSend.get(counter).getOption4());
                postDataParams.put("option4", questionToSend.get(counter).getOption4());

                Log.d("VARIABLE", Integer.toString(questionToSend.get(counter).getCorrectAnswer()));
                postDataParams.put("correctanswer", questionToSend.get(counter).getCorrectAnswer());

                Log.d("VARIABLE", String.valueOf(questionToSend.get(counter).getLongitude()));
                postDataParams.put("longitude", questionToSend.get(counter).getLongitude());

                Log.d("VARIABLE", String.valueOf(questionToSend.get(counter).getLatitude()));
                postDataParams.put("latitude", questionToSend.get(counter).getLatitude());

                counter++;

                Log.d("VARIABLE", Integer.toString(readycheck));
                postDataParams.put("finish", readycheck);
                postDataParams.put("questionidarray", JSONarrayString);

                postDataParams.put("userid", User.getInstance().getUserID());

                Log.d("VARIABLE", title);
                postDataParams.put("title", title);
                Log.d("VARIABLE", description);
                postDataParams.put("quizdescription", description);


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
                writer.write(getPostDataString(postDataParams));

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
            Log.d("PRINT", result);
            result = result.replaceAll("\\s+", "");

            Log.d("PRINT", result);
            String msg;
            if (result.equals("0")) {
                msg = "Success";
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
                toast.show();
            } else if (result.equals("-1")) {
                msg = "Quiz Title Taken";
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
                toast.show();
            }

        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPTIONQUESTION_CODE) {
            questions.addAll((ArrayList<Question>) data.getSerializableExtra("questions"));
            loadList();
        }

        if (requestCode == TIEBREAKER_CODE) {
            questions.add((Tiebreaker) data.getParcelableExtra("tiebreaker"));
            loadList();
        }
    }

    private void loadList() {
        ListView questionList = (ListView) findViewById(R.id.questionList);
        String[] values = new String[questions.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = questions.get(i).getQuestionTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        questionList.setAdapter(adapter);

        /*questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }


}