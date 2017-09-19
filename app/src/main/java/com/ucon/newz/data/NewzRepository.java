package com.ucon.newz.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ucon.newz.data.Remote.NewzRemoteDataRepository;
import com.ucon.newz.data.local.NewzLocalDataRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public class NewzRepository implements NewsDataRepository, NewsDataRepository.NewsDataLocal, NewsDataRepository.NewsDataRemote {

    private static NewzRepository INSTANCE = null;

    private NewsDataLocal mNewzLocalDataSource;

    private NewsDataRemote mNewzRemoteDataSource;

    private NewzRepository(NewsDataLocal newsLocalDataSource
    ,NewsDataRemote newzRemoteDataSource) {
        mNewzLocalDataSource = newsLocalDataSource;
        mNewzRemoteDataSource = newzRemoteDataSource;
    }


    public static NewzRepository getInstance(NewsDataLocal newzLocalDataSource,
                                             NewsDataRemote newzRemoteDataSource){
        if(INSTANCE == null){
            INSTANCE = new NewzRepository(newzLocalDataSource, newzRemoteDataSource);
        }

        return INSTANCE;
    }


    @Override
    public void getSources(@NonNull final LoadSourceCallback loadSourceCallback) throws IOException, JSONException {

        mNewzLocalDataSource.getSources(new LoadSourceCallback() {
            @Override
            public void OnTaskLoaded(List<Sources> cursor) throws IOException, JSONException {
                if(cursor.size() <= 0){
                    getSourceRemote(loadSourceCallback);
                }else {
                    loadSourceCallback.OnTaskLoaded(cursor);
                }
            }
        });

    }

    @Override
    public void getArticles(@NonNull final LoadArticlesCallback loadSourceCallback, final String sourceID, final String sortby) throws IOException, JSONException {
        mNewzLocalDataSource.getArticles(new LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                if(cursor.size() <= 0){
                    getArticleRemote(loadSourceCallback,sourceID,sortby);
                }else {
                    loadSourceCallback.OnTaskLoaded(cursor);
                }
            }
        },sourceID, sortby);
    }

    @Override
    public void getArticlesFromSearch(@NonNull final LoadArticlesCallback loadSourceCallback, String sourceID, String sortby, String title) throws IOException, JSONException {
        mNewzLocalDataSource.getArticlesFromSearch(new LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                loadSourceCallback.OnTaskLoaded(cursor);
            }
        },sourceID,sortby,title);
    }

    @Override
    public void getSourcesRemoteOnly(@NonNull LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        getSourceRemote(loadSourceCallback);
    }

    @Override
    public void getArticlesRemoteOnly(@NonNull LoadArticlesCallback loadSourceCallback, String sourceID, String sortby) throws IOException, JSONException {
        getArticleRemote(loadSourceCallback,sourceID,sortby);
    }


    private void getSourceRemote(@NonNull final LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        mNewzRemoteDataSource.getSources(new LoadSourceCallback() {
            @Override
            public void OnTaskLoaded(List<Sources> cursor) throws IOException, JSONException {
                loadSourceCallback.OnTaskLoaded(cursor);
            }
        });
    }

    private void getArticleRemote(@NonNull final LoadArticlesCallback loadSourceCallback, String sourceId, String sortBy) throws IOException, JSONException {
        mNewzRemoteDataSource.getArticles(new LoadArticlesCallback() {
            @Override
            public void OnTaskLoaded(List<Articles> cursor) throws IOException, JSONException {
                loadSourceCallback.OnTaskLoaded(cursor);
            }
        },sourceId,sortBy);
    }


}
