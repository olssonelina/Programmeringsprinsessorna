package com.example.nightingale.qwalk.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nightingale on 2017-05-08.
 */

public class Account {

    private int UserID = -1;
    private String Username;
    private List<String> Friends = new ArrayList<>();
    private List<Integer> FriendIDs = new ArrayList<>();

    public void WipeLists(){
        FriendIDs = new ArrayList<>();
        Friends = new ArrayList<>();
    }

    private static final Account instance = new Account();

    //private constructor to avoid client applications to use constructor
    private Account() {
    }

    public static Account getInstance() {
        return instance;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public List<String> getFriends() {
        return Friends;
    }

    public List<Integer> getFriendIDs() {
        return FriendIDs;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}


