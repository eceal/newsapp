package com.example.apcentece.adapter;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.apcentece.DetailActivity;
import com.example.apcentece.R;
import com.example.apcentece.model.Articles;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    ArrayList<Articles> articleList;
    LayoutInflater inflater;
    Context ctx;

    public NewsListAdapter(Context context, ArrayList<Articles> articles) {
        inflater = LayoutInflater.from(context);
        this.articleList = articles;
        this.ctx = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.laout_news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Articles selectedArticle = articleList.get(position);
        try {
            holder.setData(selectedArticle, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("articleBody", selectedArticle);
                    Intent intent = new Intent(ctx, DetailActivity.class);
                    intent.putExtras(bundle);
                    ctx.startActivity(intent);                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView articleTitle, articleDesc;
        ImageView articleImage;

        public ViewHolder(View itemView) {
            super(itemView);
            articleTitle = (TextView) itemView.findViewById(R.id.news_title_tv);
            articleDesc = (TextView) itemView.findViewById(R.id.news_detail_tv);
            articleImage = (ImageView) itemView.findViewById(R.id.news_img);

        }

        public void setData(Articles selectedArticle, int position) throws MalformedURLException {

            this.articleTitle.setText(selectedArticle.getTitle());
            this.articleDesc.setText(selectedArticle.getDescription());
            new DownloadImageFromInternet(this.articleImage).execute(selectedArticle.getUrlToImage());
        }
        @Override
        public void onClick(View v) {

        }
    }
}
