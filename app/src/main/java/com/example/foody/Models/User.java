package com.example.foody.Models;

import com.google.firebase.firestore.DocumentReference;

public class User {
    public static User user = null;

    public String fullName;
    public String userName;
    public String userEmail;
    public String profileImageUrl;
    public String userId;
    public long lastUpdate;

    private User(){
        fullName = null;
        userName = null;
        userEmail = null;
        profileImageUrl = null;
        userId = null;
        lastUpdate = 0;
    }

    public static User getInstance(){
        if(user == null)
            user = new User();
        return user;
    }

}
