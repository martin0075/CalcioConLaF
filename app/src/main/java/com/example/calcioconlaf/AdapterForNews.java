package com.example.calcioconlaf;

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
    public AdapterForNews(ArrayList<NewsElement> elenco){
        elencoNews=elenco;
    }
    @NonNull
    @Override
    public AdapterForNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newselement, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForNews.ViewHolder holder, int position) {
        Picasso.get().load(elencoNews.get(position).getURL()).into(holder.img);
        holder.orario.setText(elencoNews.get(position).getOrario()+" "+elencoNews.get(position).getAutore());
        holder.news.setText(elencoNews.get(position).getDescrizione());
        holder.news.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        return elencoNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView orario;
        public TextView news;
        public CardView Cv;
        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgLogo);
            orario = v.findViewById(R.id.txtOra);
            news=v.findViewById(R.id.txtNews);
            Cv = v.findViewById(R.id.newCard);
        }
    }
}
