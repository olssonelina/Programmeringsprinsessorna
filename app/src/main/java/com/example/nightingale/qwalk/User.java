package com.example.nightingale.qwalk;

/**
 * Created by Nightingale on 2017-05-08.
 */

public class User {

    private int UserID;
    private String Username;


    private static final User instance = new User();

    //private constructor to avoid client applications to use constructor
    private User() {
    }

    public static User getInstance() {
        return instance;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }



    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}


