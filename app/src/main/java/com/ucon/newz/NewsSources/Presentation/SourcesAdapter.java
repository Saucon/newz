package com.ucon.newz.NewsSources.Presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucon.newz.R;
import com.ucon.newz.NewsSources.domain.model.Sources;

import java.util.List;

/**
 * Created by saucon on 9/8/17.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesAdapterViewHolder> {

    private List<Sources> mCursor;
    private Context mContext;

    final private SourcesAdapterOnClickHandler mClickHandler;

    public interface SourcesAdapterOnClickHandler {
        void onClick(String sourceId);
    }

    public SourcesAdapter(Context context, SourcesAdapterOnClickHandler sourcesAdapterOnClickHandler){
        mClickHandler = sourcesAdapterOnClickHandler;
        mContext = context;
    }

    @Override
    public SourcesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_source, parent, false);
        return new SourcesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourcesAdapterViewHolder holder, int position) {
        if(mCursor.size() > 0) {
            String name = mCursor.get(position).getName();
            String desc = mCursor.get(position).getDesc();

            holder.nameSourceTextView.setText(name);
            holder.descSourceTextView.setText(desc);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.size();
    }


    void swapCursor(List<Sources> cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    class SourcesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameSourceTextView;
        TextView descSourceTextView;

        public SourcesAdapterViewHolder(View itemView) {
            super(itemView);

            nameSourceTextView = itemView.findViewById(R.id.tv_title_article);
            descSourceTextView = itemView.findViewById(R.id.tv_desc_source);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String sourceId = mCursor.get(adapterPosition).getSourceId();
            mClickHandler.onClick(sourceId);
        }
    }
}
