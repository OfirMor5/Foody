package com.example.foody.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Post implements Serializable{

    @PrimaryKey
    @NonNull
    public String postId;
    public String postTitle;
    public String postContent;
    public String postImgUrl;
    public String userId;
    public String userProfilePicUrl;
    public String username;
    public String contact;
    public long lastUpdated;

    public Post(){
    }

    public Post(String postId, String postTitle, String postContent, String postImgUrl, String userId, String userProfilePicUrl, String username){
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImgUrl = postImgUrl;
        this.userId = userId;
        this.userProfilePicUrl = userProfilePicUrl;
        this.username = username;
    }

    @NonNull
    public String getPostId() {
        return postId;
    }

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
