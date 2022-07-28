package com.example.calcioconlaf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterForNews extends RecyclerView.Adapter<AdapterForNews.ViewHolder>{
    public ArrayList<NewsElement> elencoNews;
    private OnNewsListerner onNewsListerner;

    public AdapterForNews(ArrayList<NewsElement> elenco, OnNewsListerner onNewsListerner){
        elencoNews=elenco;
        this.onNewsListerner=onNewsListerner;
    }
    @NonNull
    @Override
    public AdapterForNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newselement, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, onNewsListerner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForNews.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(elencoNews.get(position).getURL()).into(holder.img);
        holder.orario.setText(elencoNews.get(position).getOrario()+" "+elencoNews.get(position).getAutore());
        holder.news.setText(elencoNews.get(position).getDescrizione());



    }

    @Override
    public int getItemCount() {
        return elencoNews.size();
    }

    public interface OnNewsListerner{
        void onNewsClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView img;
        public TextView orario;
        public TextView news;
        public CardView Cv;
        OnNewsListerner onNewsListerner;

        public ViewHolder(View v, OnNewsListerner onNewsListerner) {
            super(v);
            img = v.findViewById(R.id.imgLogo);
            orario = v.findViewById(R.id.txtOra);
            news=v.findViewById(R.id.txtNews);
            Cv = v.findViewById(R.id.newCard);
            this.onNewsListerner=onNewsListerner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNewsListerner.onNewsClick(getAdapterPosition());
        }
    }
}
