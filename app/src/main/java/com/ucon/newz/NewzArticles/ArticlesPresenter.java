package com.ucon.newz.NewzArticles;

import android.database.Cursor;

import com.ucon.newz.data.Articles;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public class ArticlesPresenter implements ArticlesContract.Presenter {

    private NewzRepository mNewzRepository;

    private final ArticlesContract.View mArticlesView;

    private String mSourceID;
    private String mSortBy;

    private String titleQuery;

    public ArticlesPresenter(NewsDataRepository repository, ArticlesContract.View view) {
        mArticlesView = view;
        mNewzRepository = (NewzRepository) repository;

        mArticlesView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadArticles() throws IOException, JSONException {
        mNewzRepository.getArticles(new NewsDataRepository.LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                mArticlesView.loadArticlesView(cursor);
            }
        },this.getmSourceID(),this.getmSortBy());
    }

    @Override
    public void loadArticlesRemote() throws IOException, JSONException {
        mNewzRepository.getArticlesRemoteOnly(new NewsDataRepository.LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                mArticlesView.loadArticlesView(cursor);
                mArticlesView.stopRefreshView();
            }
        },this.getmSourceID(),this.getmSortBy());
    }

    @Override
    public void loadArticlesFromSearch() throws IOException, JSONException {
        if(getTitleQuery() == null || getTitleQuery().isEmpty()){
            loadArticles();
            return;
        }
        mNewzRepository.getArticlesFromSearch(new NewsDataRepository.LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                mArticlesView.loadArticlesView(cursor);
            }
        },this.getmSourceID(),this.getmSortBy(),this.getTitleQuery());
    }

    @Override
    public void getLoadArticlesParams(String sourceID, String sortby) {
        setmSourceID(sourceID);
        setmSortBy(sortby);
    }

    @Override
    public void getTitleSearch(String title) {
        setTitleQuery(title);
    }


    private String getmSourceID() {
        return mSourceID;
    }

    private void setmSourceID(String mSourceID) {
        this.mSourceID = mSourceID;
    }

    private String getmSortBy() {
        return mSortBy;
    }

    private void setmSortBy(String mSortBy) {
        this.mSortBy = mSortBy;
    }

    public String getTitleQuery() {
        return titleQuery;
    }

    public void setTitleQuery(String titleQuery) {
        this.titleQuery = titleQuery;
    }
}
