package com.example.projectsem5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewAdapter> {

    List<timerLeader> list;
    Context context;
    int i=1;

    public ScoreAdapter(List<timerLeader> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_list_item,parent,false);

        return new ScoreViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewAdapter holder, int position) {

        timerLeader tl = list.get(position);

        holder.name.setText(tl.getName());
        holder.score.setText(tl.getScore());
        holder.rank.setText(String.valueOf(list.size()-position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScoreViewAdapter extends RecyclerView.ViewHolder {

        TextView name,score,rank;

        public ScoreViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.score_user_name);
            score = itemView.findViewById(R.id.score_user_score);
            rank = itemView.findViewById(R.id.score_user_rank);
        }
    }
}
