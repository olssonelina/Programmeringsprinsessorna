package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.Quiz;

import java.util.List;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public interface IMenu {
    void openCreateNewQuiz();
    void openFriend();
    void openQuizDetails(Quiz quiz, boolean editable);
    void openHelp();
    void setListItemsUser(List<Quiz> userQuizzes);
    void setListItemsFriends(List<Quiz> friendsQuizzes);
    void setListItemsDefault(List<Quiz> defaultQuizzes);
    void setListTitleUserVisible(boolean value);
    void setListTitleFriendsVisible(boolean value);
    void setListTitleDefaultVisible(boolean value);
    void setAccountFunctionalityEnabled(boolean value);
    void setListTitleUserText(String text);
}
