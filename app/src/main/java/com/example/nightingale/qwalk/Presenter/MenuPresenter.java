package com.example.nightingale.qwalk.Presenter;

import android.os.AsyncTask;

import com.example.nightingale.qwalk.InterfaceView.IMenu;
import com.example.nightingale.qwalk.Model.Database.Account;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.Model.Tiebreaker;

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
 * Created by Elina Olsson on 2017-05-22.
 */

public class MenuPresenter {

    private IMenu view;
    private final boolean isGuest;

    private List<Quiz> userQuizzes = new ArrayList<>();
    private List<Quiz> friendQuizzes = new ArrayList<>();
    private List<Quiz> defaultQuizzes = new ArrayList<>();


    public MenuPresenter(IMenu view) {
        this.view = view;
        isGuest = Account.getInstance().getUserID() == -1;
        view.setAccountFunctionalityEnabled(!isGuest);

        view.setListTitleFriendsVisible(false);
        friendQuizUpdated();

        view.setListTitleUserVisible(false);
        if (!isGuest) {
            userQuizUpdated();
            String username = Account.getInstance().getUsername();
            if (username.charAt(username.length() - 1) == 's') {
                view.setListTitleUserText(username + " Qwalks");
            } else {
                view.setListTitleUserText(username + "s Qwalks");
            }
        }

        view.setListTitleDefaultVisible(true);
        defaultQuizzes.add(StandardQuizzes.getChalmersQuiz());
        defaultQuizzes.add(StandardQuizzes.getAdressQuiz());
        defaultQuizzes.add(StandardQuizzes.getMachineStudyRoomsQuiz());
        view.setListItemsDefault(defaultQuizzes);

    }

    public void helpButtonPressed() {
        view.openHelp();
    }

    public void friendsButtonPressed() {
        if (!isGuest) {
            view.openFriend();
        }
    }

    public void userQuizPressed(int index) {
        view.openQuizDetails(userQuizzes.get(index), true);
    }

    public void friendQuizPressed(int index) {
        view.openQuizDetails(friendQuizzes.get(index), false);
    }

    public void defaultQuizPressed(int index) {
        view.openQuizDetails(defaultQuizzes.get(index), false);
    }

    public void createQuizPressed() {
        if (!isGuest) {
            view.openCreateNewQuiz();
        }
    }

    public void userQuizUpdated() {
        userQuizzes.clear();
        loadOnlineQuizzes(Account.getInstance().getUserID(), userQuizzes);
        view.setListItemsUser(userQuizzes);
        view.setListTitleUserVisible(userQuizzes.size() != 0);
    }

    public void friendQuizUpdated() {
        friendQuizzes.clear();
        loadAllFriendsQuizzes();
        view.setListItemsFriends(friendQuizzes);
        view.setListTitleFriendsVisible(friendQuizzes.size() != 0);
    }

    private void loadAllFriendsQuizzes() {
        List<Integer> friendIDs = Account.getInstance().getFriendIDs();
        for (Integer id : friendIDs) {
            loadOnlineQuizzes(id, friendQuizzes);
        }
    }

    private int request;
    private int offset = 0;
    private int userid;
    private Tiebreaker tiebreaker;

    private void loadOnlineQuizzes(int UserID, List<Quiz> currentQuizList) {
        userid = UserID;
        request = 0;
        int quizAmount = 0;
        try {
            String JSONstring = new SendRequest().execute().get();
            JSONstring = JSONstring.replaceAll("\\s+", "");
            int RequestAmount = Integer.parseInt(JSONstring);
            quizAmount = RequestAmount;
        } catch (Exception e) {
        }

        String title = "", description = "";
        int quizID = 0;

        if (!(quizAmount == 0)) {
            request = 1;
            for (int i = 0; i < quizAmount; i++) {
                offset = i;
                try {
                    String JSONstring = new SendRequest().execute().get();

                    JSONArray jsonArray = new JSONArray(JSONstring);

                    List<Question> questions = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); ++j) {
                        if (j == 0) {
                            JSONObject quiz = jsonArray.getJSONObject(j);
                            title = quiz.getString("title");
                            description = quiz.getString("description");
                            quizID = quiz.getInt("quizid");
                        } else {

                            ArrayList<String> options = new ArrayList<>();

                            JSONObject question = jsonArray.getJSONObject(j);
                            String questionDescription = question.getString("description");
                            options.add(question.getString("option1"));
                            options.add(question.getString("option2"));
                            int correctanswer = question.getInt("correctanswer");
                            double latitude = question.getDouble("latitude");
                            double longitude = question.getDouble("longitude");
                            int questionID = question.getInt("questionid");

                            int questiontype = question.getInt("questiontype");
                            if (questiontype == 0) {
                                options.add(question.getString("option3"));
                                options.add(question.getString("option4"));
                                questions.add(new OptionQuestion(questionDescription, options, correctanswer, latitude, longitude, questionID));
                            } else if (questiontype == 1) {
                                tiebreaker = new Tiebreaker(questionDescription, correctanswer, latitude, longitude, Integer.parseInt(options.get(0)), Integer.parseInt(options.get(1)), questionID);
                            }

                        }
                    }
                    if (!(tiebreaker == null)) {
                        questions.add(tiebreaker);
                    }
                    Quiz q = new Quiz(title, description, quizID, questions);
                    currentQuizList.add(q);

                } catch (Exception e) {
                }
            }
        }
    }

    private class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(DatabaseHandler.READ_QUIZ_URL);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("request", request);
                postDataParams.put("offset", offset);
                postDataParams.put("userid", userid);

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

}
