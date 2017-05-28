package com.example.nightingale.qwalk.Presenter.Menu;

import com.example.nightingale.qwalk.Model.Quiz.Quiz;

import java.util.List;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface IMenu {
    void openCreateNewQuiz();

    void openFriend();

    void fadeFriendsIcon();

    void openQuizDetails(Quiz quiz, boolean editable);

    void setListItemsUser(List<Quiz> userQuizzes);

    void setListItemsFriends(List<Quiz> friendsQuizzes);

    void setListItemsDefault(List<Quiz> defaultQuizzes);

    void setListTitleUserVisible(boolean value);

    void setListTitleFriendsVisible(boolean value);

    void setListTitleDefaultVisible(boolean value);

    void setAccountFunctionalityEnabled(boolean value);

    void setListTitleUserText(String text);
}
