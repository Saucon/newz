package com.ucon.newz.NewzArticles;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ucon.newz.NewsSources.SourcesAdapter;
import com.ucon.newz.NewzUtillities.NewzDateUtil;
import com.ucon.newz.R;
import com.ucon.newz.data.local.NewzDBContract;

import java.text.ParseException;

/**
 * Created by saucon on 9/9/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesAdapterViewHolder> {
    private Context mContext;

    private Cursor mCursor;

    final private ArticlesAdapterOnClickHandler mClickHandler;

    public interface ArticlesAdapterOnClickHandler {
        void onClick(String url);
    }

    public ArticlesAdapter(Context context, ArticlesAdapterOnClickHandler articlesAdapterOnClickHandler){
        mClickHandler = articlesAdapterOnClickHandler;
        mContext = context;
    }

    @Override
    public ArticlesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_articles, parent, false);
        return new ArticlesAdapter.ArticlesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesAdapterViewHolder holder, int position) {
        if(mCursor.getCount() > 0){
            mCursor.moveToPosition(position);

            String title = mCursor.getString(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_TITLE));
            String desc = mCursor.getString(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_DESC));
            String sourceid = mCursor.getString(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_SOURCE_ID));
            String urlImage = mCursor.getString(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_IMAGE));
            long date = mCursor.getLong(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_DATE));

            holder.titleArticleTextView.setText(title);
            holder.descArticleTextView.setText(desc);
            holder.soureArtickeTextView.setText(sourceid);
            Glide.with(mContext).load(urlImage).into(holder.articleImageView);
            try {
                holder.dateArticleTextView.setText(NewzDateUtil.getStringFromDate(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    class ArticlesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleArticleTextView;
        TextView descArticleTextView;
        TextView soureArtickeTextView;
        TextView dateArticleTextView;
        ImageView articleImageView;

        public ArticlesAdapterViewHolder(View itemView) {
            super(itemView);

            titleArticleTextView = itemView.findViewById(R.id.tv_title_article);
            descArticleTextView = itemView.findViewById(R.id.tv_desc_article);
            soureArtickeTextView = itemView.findViewById(R.id.tv_name_source);
            dateArticleTextView = itemView.findViewById(R.id.tv_date_articles);
            articleImageView = itemView.findViewById(R.id.iv_articles);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String url = mCursor.getString(mCursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_URL));
            mClickHandler.onClick(url);
        }
    }


}
