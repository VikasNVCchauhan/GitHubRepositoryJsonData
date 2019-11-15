package com.example.githubrepositoryjson.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubrepositoryjson.R;

import java.util.ArrayList;
import java.util.List;

public class HomeRecycleAdapter extends RecyclerView.Adapter<HomeRecycleAdapter.HomeViewHolder> {

    private onItemClickListener onItemClickListener;
    private List<UserModelData> listGit;
    private Context context;

    public HomeRecycleAdapter(List<UserModelData> listGit, Context context) {
        this.listGit = listGit;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_activity_main, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.setData(listGit.get(position).getRepositoryName()
                , listGit.get(position).getLanguage(), listGit.get(position).getScore(), listGit.get(position).getWatcherCount(), listGit.get(position).getOpenIssues(), listGit.get(position).getForkCount());

    }

    @Override
    public int getItemCount() {
        return listGit.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textViewRepositoryName, textViewLanguage, textViewScore, textViewWatcher, textViewOpenIssue, textViewForksCount;
        private LinearLayout linearLayout;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            getIdForAllWidgets(itemView);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnLongClickListener(this);

        }

        private void getIdForAllWidgets(View itemView) {
            textViewRepositoryName = itemView.findViewById(R.id.text_view_repository_name_item_recycler);
            textViewLanguage = itemView.findViewById(R.id.text_view_language_item_recycler);
            textViewScore = itemView.findViewById(R.id.text_view_score_count_item_recycler);
            textViewWatcher = itemView.findViewById(R.id.text_view_watcher_count_item_recycler);
            textViewOpenIssue = itemView.findViewById(R.id.text_view_open_issue_item_recycler);
            textViewForksCount = itemView.findViewById(R.id.text_view_forks_item_recycler);
            linearLayout = itemView.findViewById(R.id.linear_layout_item_recycler_activity);
        }

        public void setData(String repository, String language, String score, String watcher, String openIssue, String forkCount) {
            textViewRepositoryName.setText(repository);
            textViewLanguage.setText(language);
            textViewScore.setText(score);
            textViewWatcher.setText(watcher);
            textViewOpenIssue.setText(openIssue);
            textViewForksCount.setText(forkCount);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onTouchListener(position);
            }
            return false;
        }
    }

    public interface onItemClickListener {
        public void onItemClick(int position);

        public void onTouchListener(int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void filteredList(ArrayList<UserModelData> arrayList) {
        listGit = arrayList;
        notifyDataSetChanged();
    }

}
