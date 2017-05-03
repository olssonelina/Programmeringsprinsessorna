package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    EditText quizTitle;
    EditText quizDescription;
    ArrayList<Question> questions = new ArrayList<>();
    private ListView questionList;

    public final static int QUESTION_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);

        quizTitle = (EditText) findViewById(R.id.quizTitleField);
        quizDescription = (EditText) findViewById(R.id.descriptionField);

    }

    public void addQuestionButtonClicked(View view) {
        Intent intent = new Intent(this, CreateQuestionActivity.class);
        startActivityForResult(intent, QUESTION_CODE);
    }

    public void createQuiz(View view) {
        if(!isQuizComplete()){
            sendErrorMsg();
        }
        else{
            saveQuiz();
        }
    }

    public boolean isQuizComplete() {
        if(quizTitle.getText().toString().equals("") || quizDescription.getText().toString().equals("") || questions.size() == 0 ){
            return false;
        }
        return true;
    }

    public void sendErrorMsg() {
        String msg;
        if(quizTitle.getText().toString().equals("")){
            msg = "Fyll i titel";
        }
        else if (quizDescription.getText().toString().equals("")) {
            msg = "Fyll i beskrivning";
        }
        else{
            msg = "Lägg till frågor";
        }
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
        toast.show();
    }

    public void saveQuiz() {
        Quiz quiz = new Quiz(quizTitle.getText().toString(), quizDescription.getText().toString());
        quiz.setQuestions(questions);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("quiz", quiz);
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("https://programmeringsprinsessorna.000webhostapp.com/validera.php");

                JSONObject postDataParams = new JSONObject();

                /*
                postDataParams.put("description", questionTitle);
                postDataParams.put("option1", questionOption1);
                postDataParams.put("option2", questionOption2);
                postDataParams.put("option3", questionOption3);
                postDataParams.put("option4", questionOption4);
                //postDataParams.put("longitude", );
                //postDataParams.put("latitude", );
                */


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
            result= result.replaceAll("\\s+","");
            if(result.equals("1")) {
                Toast.makeText(getApplicationContext(), "Admin Success",
                        Toast.LENGTH_LONG).show();

            }
            else if(result.equals("2")){
                Toast.makeText(getApplicationContext(), "Success",
                        Toast.LENGTH_LONG).show();

            }
            else if(result.equals("3")){
                Toast.makeText(getApplicationContext(), "Incorret Password/Username",
                        Toast.LENGTH_LONG).show();

            }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUESTION_CODE) {


            questions = (ArrayList<Question>) data.getSerializableExtra("questions");
            loadList();

        }
    }

    private void loadList() {
        questionList = (ListView) findViewById(R.id.questionList);
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
