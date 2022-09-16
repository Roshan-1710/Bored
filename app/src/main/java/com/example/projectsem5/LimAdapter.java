package com.example.projectsem5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LimAdapter extends RecyclerView.Adapter<LimAdapter.LimViewAdapter> {

    List<limitlessLeader> list;
    Context context;

    public LimAdapter(List<limitlessLeader> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LimViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.limit_list_item,parent,false);
        return new LimViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LimViewAdapter holder, int position) {
        limitlessLeader ll = list.get(position);
        holder.name.setText(ll.getName());
        holder.score.setText(ll.getScore());
        holder.time.setText(ll.getTime());
        holder.rank.setText(String.valueOf(list.size()-position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LimViewAdapter extends RecyclerView.ViewHolder{
        TextView name,score,rank,time;
        public LimViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.limit_user_name);
            score = itemView.findViewById(R.id.limit_user_score);
            time = itemView.findViewById(R.id.limit_user_time);
            rank = itemView.findViewById(R.id.limit_user_rank);
        }
    }
}
