package com.example.nightingale.qwalk.Model.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nightingale on 2017-05-08.
 */

public class Account {

    private int userID = -1;
    private String username = "Gäst";
    private List<String> friends = new ArrayList<>();
    private List<Integer> friendIDs = new ArrayList<>();

    private static final Account instance = new Account();

    /**
     * Clears the current friend lists
     */
    void WipeLists() {
        friendIDs = new ArrayList<>();
        friends = new ArrayList<>();
    }

    // Private constructor to avoid client applications to use constructor
    private Account() {
    }

    /**
     * @return returns the singleton accounts instance
     */
    public static Account getInstance() {
        return instance;
    }

    /**
     * @return return the users username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the current users username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return returns a list of all this users friends
     */
    public List<String> getFriends() {
        return friends;
    }

    /**
     * @return returns a list of all this users friends ID´s
     */
    public List<Integer> getFriendIDs() {
        return friendIDs;
    }

    /**
     * @return returns the ID of this user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets this users ID
     *
     * @param userID used in database
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Resets the account
     */
    public void logOut() {
        userID = -1;
        username = "Gäst";
        WipeLists();

    }
}


