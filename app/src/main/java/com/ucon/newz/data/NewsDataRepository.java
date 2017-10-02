package com.ucon.newz.data;

import android.support.annotation.NonNull;

import com.ucon.newz.NewsSources.domain.model.Sources;
import com.ucon.newz.NewzArticles.domain.model.Articles;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public interface NewsDataRepository {

    interface NewsDataLocal{
        void getSources(@NonNull LoadSourceCallback loadSourceCallback) throws IOException, JSONException;

        void getArticles(@NonNull LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException;

        void getArticlesFromSearch(@NonNull LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby, String title) throws IOException, JSONException;

    }

    interface NewsDataRemote {
        void getArticles(@NonNull LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException;

        void getSources(@NonNull LoadSourceCallback loadSourceCallback) throws IOException, JSONException;


        void getArticlesRemoteOnly(@NonNull LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException;

        void getSourcesRemoteOnly(@NonNull LoadSourceCallback loadSourceCallback) throws IOException, JSONException;

    }

    interface LoadSourceCallback{
        void OnTaskLoaded (List<Sources> cursor) throws IOException, JSONException;
    }

    interface LoadArticlesCallback{
        void OnTaskLoaded (List<Articles> cursor) throws IOException, JSONException;
    }

    void getSources(@NonNull LoadSourceCallback loadSourceCallback) throws IOException, JSONException;

    void getArticles(@NonNull LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException;


}
