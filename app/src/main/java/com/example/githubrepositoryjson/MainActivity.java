package com.example.githubrepositoryjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.githubrepositoryjson.HomePage.HomeRecycleAdapter;
import com.example.githubrepositoryjson.HomePage.UserModelData;
import com.example.githubrepositoryjson.UserDetail.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements HomeRecycleAdapter.onItemClickListener, TextWatcher, View.OnClickListener {


    private HomeRecycleAdapter homeRecycleAdapter;
    private AlertDialog alertDialog;
    //Widgets
    private LinearLayout progressBarLinearLayout;
    private RecyclerView recyclerView;
    private TextView textViewRepositoryName, textViewLanguage, textViewScore, textViewWatcher, textViewOpenIssue, textViewForksCount, textViewDescription;
    private EditText editTextSearchData;
    private ImageView imageViewClear;
    //Variables
    private List<UserModelData> listGit;

    //Json Query
    private RequestQueue mRequestQueue;
    private static final String jsonUrl = "https://api.github.com/search/repositories?q=android+org:google";
    private String repositoryName, language, score, watcherCount, openIssues, forkCount, description, follower, following, events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdForAllWidgets();
        listGit = new ArrayList<>();
        setDataInList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearchData.addTextChangedListener(this);
        imageViewClear.setOnClickListener(this);


    }


    private void setDataInList() {

        mRequestQueue = Volley.newRequestQueue(this);
        jsonPars();
    }

    private void jsonPars() {

        progressBarLinearLayout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectJson = new JSONObject(response);
                            JSONArray array = objectJson.getJSONArray("items");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jObject = array.getJSONObject(i);

                                JSONObject inJSONObject1 = jObject.getJSONObject("owner");
                                follower= inJSONObject1.getString("followers_url");
                                following = inJSONObject1.getString("following_url");

                                repositoryName = jObject.getString("name");
                                language = jObject.getString("language");
                                score = jObject.getString("score");
                                description = jObject.getString("description");
                                events = jObject.getString("events_url");

                                watcherCount = checkDataSize(jObject.getString("watchers"));
                                openIssues = checkDataSize(jObject.getString("open_issues"));
                                forkCount = checkDataSize(jObject.getString("forks"));

                                listGit.add(new UserModelData(repositoryName, language, score, watcherCount, openIssues, forkCount, description, follower, following, events));

                            }
                            homeRecycleAdapter = new HomeRecycleAdapter(listGit, MainActivity.this);
                            recyclerView.setAdapter(homeRecycleAdapter);
                            homeRecycleAdapter.setOnItemClickListener(MainActivity.this);

                            homeRecycleAdapter.notifyDataSetChanged();
                            progressBarLinearLayout.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarLinearLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    private String checkDataSize(String count) {
                        String returnCount = null;
                        if (Float.parseFloat(count) > 1000) {
                            double countDouble = (Float.parseFloat(count) / 1000);

                            System.out.println("DOUBLE COUNT : " + countDouble
                            );
                            returnCount = new DecimalFormat("###.#").format(countDouble) + "K";

                        } else if (Float.parseFloat(count) > 1000000) {
                            double countDouble = (Float.parseFloat(count) / 1000);

                            System.out.println("DOUBLE COUNT : " + countDouble
                            );
                            returnCount = new DecimalFormat("###.#").format(countDouble) + "M";

                        } else {
                            returnCount = count;
                        }
                        return returnCount;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBarLinearLayout.setVisibility(View.INVISIBLE);
            }
        });
        mRequestQueue.add(stringRequest);
    }

    private void getIdForAllWidgets() {
        recyclerView = findViewById(R.id.recycler_view_activity_main);
        editTextSearchData = findViewById(R.id.edit_text_toolbar_layout);
        imageViewClear = findViewById(R.id.image_view_close_header_toolbar);
        progressBarLinearLayout = findViewById(R.id.linerar_layout_progress_bar_main_activity);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, UserDetail.class);
        intent.putExtra("REPO_NAME", listGit.get(position).getRepositoryName());
        intent.putExtra("LANGUAGE", listGit.get(position).getLanguage());
        intent.putExtra("SCORE", listGit.get(position).getScore());
        intent.putExtra("FOLLOWING", listGit.get(position).getFollowing());
        intent.putExtra("FOLLOWERS", listGit.get(position).getFollower());
        intent.putExtra("EVENTS", listGit.get(position).getEvents());
        intent.putExtra("DESCRIPTION", listGit.get(position).getDescription());
        intent.putExtra("FORKS_COUNT", listGit.get(position).getForkCount());
        intent.putExtra("WATCHER_COUNT", listGit.get(position).getWatcherCount());
        intent.putExtra("OPEN_ISSUE_COUNT", listGit.get(position).getOpenIssues());

        startActivity(intent);
    }

    @Override
    public void onTouchListener(int position) {

        if (editTextSearchData.getText().toString().equals(" ") || !editTextSearchData.getText().toString().equals("")) {
            return;
        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null);
            getIdForAllWidgets(view);
            alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setView(view)
                    .show();
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 40);    //here margin from left and right are set
            alertDialog.getWindow().setBackgroundDrawable(inset);

            setDataToDialog(position);
        }
    }

    private void setDataToDialog(int position) {
        textViewRepositoryName.setText(listGit.get(position).getRepositoryName());
        textViewLanguage.setText(listGit.get(position).getLanguage());
        textViewScore.setText(listGit.get(position).getScore());
        textViewWatcher.setText(listGit.get(position).getWatcherCount());
        textViewOpenIssue.setText(listGit.get(position).getOpenIssues());
        textViewForksCount.setText(listGit.get(position).getForkCount());
        textViewDescription.setText('"' + listGit.get(position).getDescription() + '"');
    }

    private void getIdForAllWidgets(View view) {
        textViewRepositoryName = view.findViewById(R.id.textView_repository_name_user_detail);
        textViewLanguage = view.findViewById(R.id.textView_language_user_detail);
        textViewScore = view.findViewById(R.id.textView_score_user_detail);
        textViewWatcher = view.findViewById(R.id.text_view_watcher_user_detail);
        textViewOpenIssue = view.findViewById(R.id.text_view_open_issue_user_detail);
        textViewDescription = view.findViewById(R.id.text_view_user_description_custom_dialog);
        textViewForksCount = view.findViewById(R.id.text_view_forks_item_user_detail);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (!editTextSearchData.getText().equals("")) {
            imageViewClear.setVisibility(View.VISIBLE);
        } else {
            imageViewClear.setVisibility(View.INVISIBLE);
        }
        filter(editable.toString());
    }

    private void filter(String toString) {
        ArrayList<UserModelData> arrayList = new ArrayList<>();
        for (UserModelData userModelData : listGit) {
            if (checkDataInRecycle(userModelData, toString)) {
                arrayList.add(userModelData);
            }
        }
        homeRecycleAdapter.filteredList(arrayList);
    }

    private boolean checkDataInRecycle(UserModelData userModelData, String toString) {
        boolean returnType;
        if (userModelData.getRepositoryName().toLowerCase().contains(toString.toLowerCase()) || userModelData.getLanguage().toLowerCase().contains(toString.toLowerCase())) {
            returnType = true;
        } else {
            returnType = false;
        }
        return returnType;
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewClear) {
            editTextSearchData.setText("");
            imageViewClear.setVisibility(View.INVISIBLE);
        }
    }
}
