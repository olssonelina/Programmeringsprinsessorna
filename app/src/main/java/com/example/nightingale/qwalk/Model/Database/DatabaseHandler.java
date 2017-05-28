package com.example.nightingale.qwalk.Model.Database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nightingale.qwalk.Model.MessageMediator.IOnMessageRecievedListener;
import com.example.nightingale.qwalk.Model.MessageMediator.MessageMediator;
import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nightingale on 2017-05-15.
 *
 * This class is responsible for all communication with the database online
 */

public final class DatabaseHandler {
    private static String quizDescription;
    private static String quizTitle;
    private static Quiz quiz;
    private static String JSONarrayString;
    private static String response;
    private static int readycheck = 0;
    private static int request;
    private static String friendUsername;
    private static int quizID;
    private static List<Integer> questionIDArray = new ArrayList<Integer>();
    private static int counter = 0;
    private static List<Question> quizQuestions = new ArrayList<>();
    private static String username;
    private static String password;
    private static int offset;
    private static int userid;

    final private static String HOST = "programmeringsprinsessorna.000webhostapp.com";
    final private static String INSERT_QUIZ_URL = "https://programmeringsprinsessorna.000webhostapp.com/insertquiz.php";
    final private static String INSERT_ACCOUNT_URL = "https://programmeringsprinsessorna.000webhostapp.com/insert.php";
    final private static String DELETE_QUIZ_URL = "https://programmeringsprinsessorna.000webhostapp.com/deletequiz.php";
    final private static String DELETE_FRIEND_URL = "https://programmeringsprinsessorna.000webhostapp.com/deletefriend.php";
    final private static String INSERT_FRIEND_URL = "https://programmeringsprinsessorna.000webhostapp.com/insertfriend.php";
    final private static String VALIDATE_URL = "https://programmeringsprinsessorna.000webhostapp.com/validera.php";
    final private static String READ_QUIZ_URL = "https://programmeringsprinsessorna.000webhostapp.com/readquiz.php";

    private static MessageMediator mediator = new MessageMediator();

    public static void setOnMessageRecievedListener(IOnMessageRecievedListener listener) {
        mediator.addListener(listener);
    }

    private DatabaseHandler() {
    }




    public static String getPostDataString(JSONObject params)  throws UnsupportedEncodingException, JSONException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first){
                first = false;
            }
            else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    public static void loadFriends() {

        Log.d("JSON", "Method entered");
        request = 1;
        friendUsername = "";
        try {
            String JSONstring = new SendFriendRequest().execute().get();

            Log.d("JSON", JSONstring);
            JSONArray jsonArray = new JSONArray(JSONstring);

            Account.getInstance().wipeLists();
            for (int i = 0; i < jsonArray.length(); ++i) {

                JSONObject quiz = jsonArray.getJSONObject(i);
                String friend = quiz.getString("username");
                Log.d("JSON", friend);
                Account.getInstance().getFriends().add(friend);

                int friendID = quiz.getInt("accountid");
                Log.d("JSON", String.valueOf(friendID));
                Account.getInstance().getFriendIDs().add(friendID);
            }

        } catch (Exception e) {
            Log.d("JSON", "Crash2Handler");
        }
        request = 0;
    }

    public static void addFriend(String friend) {
        if(friend.equals("")){
            return;
        }
        else{


        friendUsername = friend;
        request = 0;

        new DatabaseHandler.SendFriendRequest().execute();

        }
    }

    public static void deleteFriend(String friend) {

        friendUsername = friend;

        new DatabaseHandler.SendDeleteFriendRequest().execute();


    }

    public static void deleteQuiz(int quiz) {

        quizID = quiz;

        new DatabaseHandler.SendDeleteQuizRequest().execute();


    }


    public static void registerAccount(String username, String password) {

        DatabaseHandler.username = username;
        DatabaseHandler.password = password;

        new DatabaseHandler.SendRegisterRequest().execute();


    }

    public static void login(String username, String password) {

        DatabaseHandler.username = username;
        DatabaseHandler.password = password;

        new DatabaseHandler.SendLoginRequest().execute();


    }


    public static String readQuiz(int readRequest, int readOffset, int readUserid) {
        request = readRequest;
        offset = readOffset;
        userid = readUserid;
try {
    return new DatabaseHandler.SendReadRequest().execute().get();
}
catch (Exception e) {
    }
        return "";
    }

    public static String sendParams(URL url, JSONObject postDataParams) {

        try {

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
                return "false : " + responseCode;
            }

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }

    }


    public static void saveQuiz(String title, String description, List<Question> questions, Quiz editQuiz) {
        Log.d("VARIABLE QuizID", "Stuff happening");

        quiz = editQuiz;
        quizDescription = description;
        quizTitle = title;
        quizQuestions = questions;

        questionIDArray = new ArrayList<Integer>();
        counter = 0;
        readycheck = 0;

        for (int i = 0; i < quizQuestions.size(); i++) {
            Log.d("Test", "In loop");
            try {

                response = new SendInsertQuizRequest().execute().get();
                Log.d("Getcomplete", "True");
                Log.d("Getcomplete", response);
                response = response.replaceAll("\\s+", "");
                questionIDArray.add(Integer.parseInt(response));
            } catch (Exception e) {
                Log.d("Getcomplete", "False");
                break;
            }
            Log.d("Getcomplete", "Test");
        }
        Log.d("VARIABLE QuizID", "out of loop");

        try {
        Log.d("JSONindex", String.valueOf(questionIDArray.get(0)));
        } catch (Exception e) {
        }
        JSONArray jsArray = new JSONArray(questionIDArray);

        JSONarrayString = jsArray.toString();
        Log.d("JSON", JSONarrayString);
        readycheck = 1;
        counter = 0;
        new SendInsertQuizRequest().execute();

    }


    public static class SendInsertQuizRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
        }

        protected final String doInBackground(String... arg0) {

            try {


                URL url = new URL(INSERT_QUIZ_URL);

                JSONObject postDataParams = new JSONObject();


                if(quiz == null){
                    postDataParams.put("quizid", -1);
                }
                    else{
                    postDataParams.put("quizid", quiz.getQuizID());
                    Log.d("VARIABLE QuizID", String.valueOf(quiz.getQuizID()));
                }


                if (quizQuestions.get(counter) instanceof OptionQuestion) {
                    Log.d("VARIABLE", "Type : 0");
                    postDataParams.put("questiontype", 0);

                    Log.d("VARIABLE", ((OptionQuestion) quizQuestions.get(counter)).getOption(0));
                    postDataParams.put("option1", ((OptionQuestion) quizQuestions.get(counter)).getOption(0));

                    Log.d("VARIABLE", ((OptionQuestion) quizQuestions.get(counter)).getOption(1));
                    postDataParams.put("option2", ((OptionQuestion) quizQuestions.get(counter)).getOption(1));

                    Log.d("VARIABLE", ((OptionQuestion) quizQuestions.get(counter)).getOption(2));
                    postDataParams.put("option3", ((OptionQuestion) quizQuestions.get(counter)).getOption(2));

                    Log.d("VARIABLE", ((OptionQuestion) quizQuestions.get(counter)).getOption(3));
                    postDataParams.put("option4", ((OptionQuestion) quizQuestions.get(counter)).getOption(3));
                } else if (quizQuestions.get(counter) instanceof Tiebreaker) {
                    Log.d("VARIABLE", "Type : 1");
                    postDataParams.put("questiontype", 1);

                    Log.d("VARIABLE", String.valueOf(((Tiebreaker) quizQuestions.get(counter)).getLowerBounds()));
                    postDataParams.put("option1", ((Tiebreaker) quizQuestions.get(counter)).getLowerBounds());

                    Log.d("VARIABLE", String.valueOf(((Tiebreaker) quizQuestions.get(counter)).getUpperBounds()));
                    postDataParams.put("option2", ((Tiebreaker) quizQuestions.get(counter)).getUpperBounds());


                    postDataParams.put("option3", "");
                    postDataParams.put("option4", "");
                }


                Log.d("VARIABLE", quizQuestions.get(counter).getQuestionTitle());
                postDataParams.put("description", quizQuestions.get(counter).getQuestionTitle());

                Log.d("VARIABLE QuestionID", String.valueOf(quizQuestions.get(counter).getQuestionID()));
                postDataParams.put("questionid", quizQuestions.get(counter).getQuestionID());


                Log.d("VARIABLE", Integer.toString(quizQuestions.get(counter).getCorrectAnswer()));
                postDataParams.put("correctanswer", quizQuestions.get(counter).getCorrectAnswer());

                Log.d("VARIABLE", String.valueOf(quizQuestions.get(counter).getLongitude()));
                postDataParams.put("longitude", quizQuestions.get(counter).getLongitude());

                Log.d("VARIABLE", String.valueOf(quizQuestions.get(counter).getLatitude()));
                postDataParams.put("latitude", quizQuestions.get(counter).getLatitude());

                counter++;

                Log.d("VARIABLE", Integer.toString(readycheck));
                postDataParams.put("finish", readycheck);
                postDataParams.put("questionidarray", JSONarrayString);

                postDataParams.put("userid", Account.getInstance().getUserID());

                Log.d("VARIABLE", quizTitle);
                postDataParams.put("title", quizTitle);
                Log.d("VARIABLE", quizDescription);
                postDataParams.put("quizdescription", quizDescription);


                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);

            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected final void onPostExecute(String result) {

            Log.d("PRINT", result);
            Log.d("PRINT", result);
            result = result.replaceAll("\\s+", "");

            Log.d("PRINT", result);
            String msg = "";
            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("0")) {
                msg = "Quiz Tillagd"; // "Success" -> Klar

            } else if (result.equals("-1")) {
                msg = "Quiz titel upptagen"; //"Quiz Title Taken" översatt
            }
            else if (result.equals("-2")) {
                msg = "Quiz Uppdaterad"; //"Quiz Title Taken" översatt
            }
            else if (result.equals("Exception:Unabletoresolvehost\"" + HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Uppkoppling misslyckades";
            }
            else{
                msg = "Ignore"; //"Quiz Title Taken" översatt
            }

            mediator.onMessageRecieved(msg);

        }
    }


    public static class SendFriendRequest extends AsyncTask<String, Void, String> {

        private String username = friendUsername;

        protected void onPreExecute() {
        }

        protected final String doInBackground(String... arg0) {

            try {

                URL url = new URL(INSERT_FRIEND_URL);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("friendusername", username);
                postDataParams.put("userid", Account.getInstance().getUserID());
                postDataParams.put("request", request);


                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);


            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected final void onPostExecute(String result) {

            String msg = "";


            Log.e("response", result);
            result = result.replaceAll("\\s+", "");

            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("0")) {
                msg = "Error";
            } else if (result.equals("1")) {
                msg = "Användarnamnet finns inte";
            } else if (result.equals("2")) {
                msg = "Du är redan vän med den här personen";
            } else if (result.equals("3")) {
                msg = "Vän tillagd";
            } else if (result.equals("Exception:Unabletoresolvehost\"" + HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Uppkoppling misslyckades";
            }
            else{
                msg = "Ignore";
            }

            mediator.onMessageRecieved(msg);


        }
    }

    public static class SendDeleteQuizRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
        }

        protected final String doInBackground(String... arg0) {

            try {

                URL url = new URL(DELETE_QUIZ_URL);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("quizid", quizID);

                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected final void onPostExecute(String result) {

            String msg = "";


            Log.e("response", result);
            result = result.replaceAll("\\s+", "");

            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("0")) {
                msg = "Success";

            }
            else if (result.equals("Exception:Unabletoresolvehost\"" + HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Uppkoppling misslyckades";
            }
            else {
                msg = "Error";
            }
            mediator.onMessageRecieved(msg);


        }
    }

    public static class SendDeleteFriendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
        }

        protected final String doInBackground(String... arg0) {

            try {

                URL url = new URL(DELETE_FRIEND_URL);

                JSONObject postDataParams = new JSONObject();



                postDataParams.put("friendid", Account.getInstance().getFriendIDs().get(Account.getInstance().getFriends().indexOf(friendUsername)));
                postDataParams.put("accountid", Account.getInstance().getUserID());

                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected final void onPostExecute(String result) {

            String msg = "";


            Log.e("response", result);
            result = result.replaceAll("\\s+", "");

            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("Exception:Unabletoresolvehost\"" + HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Uppkoppling misslyckades";
            }
            else if (result.equals("0")) {
                msg = "Vän borttagen";
            } else {
                msg = "Error";
            }
            mediator.onMessageRecieved(msg);


        }
    }


    public static class SendRegisterRequest extends AsyncTask<String, Void, String> {



        protected void onPreExecute() {
        }

        protected final String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.INSERT_ACCOUNT_URL);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("username", username);
                postDataParams.put("password", password);


                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected final void onPostExecute(String result) {

            result = result.replaceAll("\\s+", "");

String msg = "";

            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("Exception:Unabletoresolvehost\"" + HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Uppkoppling misslyckades";
            }
            else if (result.equals("0")) {
                msg = "Registrering lyckad";


            } else if (result.equals("1")) {
                msg = "Användarnamn upptaget";

            } else if (result.equals("2")) {
                msg = "Fyll i användarnamn";

            } else if (result.equals("3")) {
                msg = "Fyll i lösenord";

            }
            else{
                msg = "Error";
            }
            mediator.onMessageRecieved(msg);

        }
    }

    private static class SendLoginRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.VALIDATE_URL);

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("username", username);
                postDataParams.put("password", password);


                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String result) {

            result = result.replaceAll("\\s+", "");
            Log.e("CrashID", result);
            String msg = "";
            if (result == null || result.equals("<br/>") || result.equals("false:500")){
                msg = "Anslutning misslyckades";
            }
            else if (result.equals("Exception:Unabletoresolvehost\"" + DatabaseHandler.HOST + "\":Noaddressassociatedwithhostname")) {
                msg = "Inget internet";
            }
            else if (result.equals("timeout")){
                msg = "Timed out";
            }
            else if (Integer.parseInt(result) == -1) {
                msg = "Fel lösenord/användarnamn!";
            } else if (Integer.parseInt(result) == -2) {
                Account.getInstance().setUserID(Integer.parseInt(result));
                Account.getInstance().setUsername(username);
                DatabaseHandler.loadFriends();

                msg = "Loggar in";

                //Här kan man implementera admin funktioner


            } else {
                Account.getInstance().setUserID(Integer.parseInt(result));
                Account.getInstance().setUsername(username);
                DatabaseHandler.loadFriends();

                msg = "Loggar in";
            }

            mediator.onMessageRecieved(msg);
        }
    }

    private static class SendReadRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.READ_QUIZ_URL);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("request", request);
                postDataParams.put("offset", offset);
                postDataParams.put("userid", userid);

                Log.e("params", postDataParams.toString());

                return sendParams(url, postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String result) {
        }
    }

}
