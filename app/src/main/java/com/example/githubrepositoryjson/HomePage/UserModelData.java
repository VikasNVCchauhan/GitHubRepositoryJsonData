package com.example.githubrepositoryjson.HomePage;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import static android.os.UserHandle.readFromParcel;


public class UserModelData {
    private String repositoryName, language, score, watcherCount, openIssues, forkCount, description, follower, following, events;

    public UserModelData(String repositoryName, String language, String score, String watcherCount, String openIssues, String forkCount, String description, String follower, String following, String events) {
        this.repositoryName = repositoryName;
        this.score = score;
        this.watcherCount = watcherCount;
        this.openIssues = openIssues;
        this.language = language;
        this.forkCount = forkCount;
        this.description = description;
        this.follower = follower;
        this.following = following;
        this.events = events;

    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getScore() {
        return score;
    }

    public String getWatcherCount() {
        return watcherCount;
    }

    public String getOpenIssues() {
        return openIssues;
    }

    public String getForkCount() {
        return forkCount;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getFollower() {
        return follower;
    }

    public String getFollowing() {
        return following;
    }

    public String getEvents() {
        return events;
    }
}
