package com.example.calcioconlaf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForLeaderboard extends RecyclerView.Adapter<AdapterForLeaderboard.ViewHolder>{
    public ArrayList<LeaderboardElement> elencoPunteggi;
    private OnLeaderboardListener onLeaderboardListerner;
    public Context context;

    public AdapterForLeaderboard(ArrayList<LeaderboardElement> elencoPunteggi, FragmentManager activity) {
        this.elencoPunteggi = elencoPunteggi;
        this.onLeaderboardListerner = onLeaderboardListerner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboardelement, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(elencoPunteggi.get(position).getUsername());
        holder.punteggio.setText(String.valueOf(elencoPunteggi.get(position).getPunteggio()));
    }

    @Override
    public int getItemCount() {
        return elencoPunteggi.size();
    }
    public interface OnLeaderboardListener{
        void onLeaderboardClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        OnLeaderboardListener onLeaderboardListerner;
        public ImageView img;
        public TextView username;
        public TextView punteggio;

        public ViewHolder(View v) {
            super(v);
            this.onLeaderboardListerner=onLeaderboardListerner;
            img=v.findViewById(R.id.imgLeaderboard);
            username=v.findViewById(R.id.textUsername);
            punteggio=v.findViewById(R.id.textPunteggio);
        }
    }
}
