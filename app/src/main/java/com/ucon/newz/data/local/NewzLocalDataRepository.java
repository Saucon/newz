package com.ucon.newz.data.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ucon.newz.data.Articles;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public class NewzLocalDataRepository implements NewsDataRepository.NewsDataLocal {

    private static NewzLocalDataRepository INSTANCE;

    private Context mContext;

    private NewzLocalDataRepository(Context context){
        mContext = context;
    }

    public static NewzLocalDataRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new NewzLocalDataRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {

        Cursor cursor = mContext.getContentResolver().query(
                NewzDBContract.SourceEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        loadSourceCallback.OnTaskLoaded(Sources.getSourcesList(cursor));
    }

    @Override
    public void getArticles(@NonNull NewsDataRepository.LoadArticlesCallback loadSourceCallback, String sourceID, String sortby) throws IOException, JSONException {

        Cursor cursor = mContext.getContentResolver().query(
                NewzDBContract.ArticlesEntry.buildArticleUri(sourceID,sortby),
                null,
                null,
                new String[]{sourceID},
                sortby
        );

        loadSourceCallback.OnTaskLoaded(Articles.getArticlesList(cursor,sourceID));
    }

    @Override
    public void getArticlesFromSearch(@NonNull NewsDataRepository.LoadArticlesCallback loadSourceCallback, String sourceID, String sortby, String title) throws IOException, JSONException {
        Cursor cursor = mContext.getContentResolver().query(
                NewzDBContract.ArticlesEntry.buildArticleUriWithTitle(sourceID,sortby,title),
                null,
                null,
                new String[]{sourceID,"%"+title+"%"},
                sortby
        );

        loadSourceCallback.OnTaskLoaded(Articles.getArticlesList(cursor, sourceID));
    }
}
