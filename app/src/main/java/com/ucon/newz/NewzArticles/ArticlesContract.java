package com.ucon.newz.NewzArticles;

import android.database.Cursor;

import com.ucon.newz.BasePresenter;
import com.ucon.newz.BaseView;
import com.ucon.newz.data.Articles;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public interface ArticlesContract {
    interface View extends BaseView<ArticlesContract.Presenter>{
        void loadArticlesView(List<Articles> cursor);
        void stopRefreshView();
    }

    interface Presenter extends BasePresenter{
        void loadArticles() throws IOException, JSONException;
        void loadArticlesRemote() throws IOException, JSONException;
        void loadArticlesFromSearch() throws IOException, JSONException;
        void getLoadArticlesParams(String sourceID, String sortby);
        void getTitleSearch(String title);
    }
}
