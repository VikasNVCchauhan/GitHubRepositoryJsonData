package com.example.githubrepositoryjson.UserDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubrepositoryjson.HomePage.UserModelData;
import com.example.githubrepositoryjson.MainActivity;
import com.example.githubrepositoryjson.R;

import java.util.ArrayList;
import java.util.List;


public class UserDetail extends AppCompatActivity implements View.OnClickListener {

    //Widgets
    private TextView textViewRepositoryName, textViewLanguage, textViewScore, textViewWatcher, textViewOpenIssue, textViewForksCount, textViewDescription, textViewFollower, textViewFollowing, textViewEvent;
    private ImageView imageViewBack;
    //Variable
    private String repositoryName, language, score, watcherCount, openIssues, forkCount, description, follower, following, events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getIdForAllWidgets();

        repositoryName = getIntent().getStringExtra("REPO_NAME");
        language = getIntent().getStringExtra("LANGUAGE");
        score = getIntent().getStringExtra("SCORE");
        watcherCount = getIntent().getStringExtra("WATCHER_COUNT");
        openIssues = getIntent().getStringExtra("OPEN_ISSUE_COUNT");
        forkCount = getIntent().getStringExtra("FORKS_COUNT");
        description = getIntent().getStringExtra("DESCRIPTION");
        follower = getIntent().getStringExtra("FOLLOWERS");
        following = getIntent().getStringExtra("FOLLOWING");
        events = getIntent().getStringExtra("EVENTS");

        setData();

        imageViewBack.setOnClickListener(this);
        textViewFollower.setOnClickListener(this);
        textViewFollowing.setOnClickListener(this);
        textViewEvent.setOnClickListener(this);
    }

    public void open(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    private void setData() {
        textViewRepositoryName.setText(repositoryName);
        textViewLanguage.setText(language);
        textViewScore.setText(score);
        textViewWatcher.setText(watcherCount);
        textViewOpenIssue.setText(openIssues);
        textViewForksCount.setText(forkCount);
        textViewDescription.setText('"' + description + '"');

    }


    private void getIdForAllWidgets() {
        textViewRepositoryName = findViewById(R.id.textView_repository_name_user_detail);
        textViewLanguage = findViewById(R.id.textView_language_user_detail);
        textViewScore = findViewById(R.id.textView_score_user_detail);
        textViewWatcher = findViewById(R.id.text_view_watcher_user_detail);
        textViewOpenIssue = findViewById(R.id.text_view_open_issue_user_detail);
        textViewForksCount = findViewById(R.id.text_view_forks_item_user_detail);
        textViewDescription = findViewById(R.id.text_view_user_description_user_detail);
        textViewFollower = findViewById(R.id.text_view_followers_user_detail);
        textViewFollowing = findViewById(R.id.text_view_followings_user_detail);
        textViewEvent = findViewById(R.id.text_view_events_user_detail);
        imageViewBack = findViewById(R.id.image_view_back_toolbar_user_detail);
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewBack) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (view == textViewFollower) {
            open(follower);
        } else if (view == textViewFollowing) {
            open(following);
        } else if (view == textViewEvent) {
            open(events);
        }
    }
}
