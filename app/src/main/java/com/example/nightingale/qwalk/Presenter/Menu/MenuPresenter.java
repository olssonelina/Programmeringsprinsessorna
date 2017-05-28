package com.example.nightingale.qwalk.Presenter.Menu;

import com.example.nightingale.qwalk.Model.Database.Account;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.StandardQuizzes;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        if(isGuest)  {
            view.fadeFriendsIcon();
        }

        view.setListTitleDefaultVisible(true);
        defaultQuizzes.add(StandardQuizzes.getChalmersQuiz());
        defaultQuizzes.add(StandardQuizzes.getAdressQuiz());
        defaultQuizzes.add(StandardQuizzes.getMachineStudyRoomsQuiz());
        view.setListItemsDefault(defaultQuizzes);

    }


    public final void friendsButtonPressed() {
        if (!isGuest) {
            view.openFriend();
        }
    }

    public final void userQuizPressed(int index) {
        view.openQuizDetails(userQuizzes.get(index), true);
    }

    public final void friendQuizPressed(int index) {
        view.openQuizDetails(friendQuizzes.get(index), false);
    }

    public final void defaultQuizPressed(int index) {
        view.openQuizDetails(defaultQuizzes.get(index), false);
    }

    public final void createQuizPressed() {
        if (!isGuest) {
            view.openCreateNewQuiz();
        }
    }

    public final void userQuizUpdated() {
        userQuizzes.clear();
        loadOnlineQuizzes(Account.getInstance().getUserID(), userQuizzes);
        view.setListItemsUser(userQuizzes);
        view.setListTitleUserVisible(userQuizzes.size() != 0);
    }

    public final void friendQuizUpdated() {
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

    private int offset = 0;
    private Tiebreaker tiebreaker;

    private void loadOnlineQuizzes(int userID, List<Quiz> currentQuizList) {
        int userid = userID;
        int request = 0;
        int quizAmount = 0;
        try {
            String JSONstring = DatabaseHandler.readQuiz(request, offset, userid);
            JSONstring = JSONstring.replaceAll("\\s+", "");
            int requestAmount = Integer.parseInt(JSONstring);
            quizAmount = requestAmount;

        } catch (Exception e) {
        }

        String title = "", description = "";
        int quizID = 0;

        if (!(quizAmount == 0)) {
            request = 1;
            for (int i = 0; i < quizAmount; i++) {
                offset = i;
                try {

                    String JSONstring = DatabaseHandler.readQuiz(request, offset, userid);

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

                } catch (JSONException j) {
                }
            }
        }
    }

}
