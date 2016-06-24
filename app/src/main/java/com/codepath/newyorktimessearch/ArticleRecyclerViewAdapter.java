package com.codepath.newyorktimessearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sophiehouser on 6/21/16.
 */
public class ArticleRecyclerViewAdapter extends
        RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

    private List<Article> articles;
    // Store the context for easy access
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView imageView;
        // Store a member variable for the contacts

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }

    public ArticleRecyclerViewAdapter(Context context, List<Article> articleList) {
        articles = articleList;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ArticleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = articles.get(position);

        // Set item views based on the data model
        TextView title = viewHolder.tvTitle;
        title.setText(article.getHeadline());

        ImageView image = viewHolder.imageView;
        image.setImageResource(0);

        String thumbnail = article.getThumbNail();

        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(image);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addAll(ArrayList<Article> articleList){
        articles.addAll(articleList);
        notifyDataSetChanged();
    }

    public void clear(){
        articles.clear();
        notifyDataSetChanged();
    }
}
