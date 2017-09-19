package com.ucon.newz.data.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

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
        List<Sources> sourcesList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                NewzDBContract.SourceEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if(cursor!=null || cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int _ID = cursor.getInt(cursor.getColumnIndex(NewzDBContract.SourceEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_DESC));
                String sourceId = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_SOURCE_ID));
                Sources sources = new Sources(_ID, name, desc, sourceId);
                sourcesList.add(sources);
            }
        }

        if(cursor != null){
            cursor.close();
        }

        loadSourceCallback.OnTaskLoaded(sourcesList);
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
        loadSourceCallback.OnTaskLoaded(cursor);
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
        loadSourceCallback.OnTaskLoaded(cursor);
    }

}
