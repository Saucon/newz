package com.ucon.newz.data.Remote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ucon.newz.NewzUtillities.NetworkUtils;
import com.ucon.newz.NewzUtillities.NewsJsonUtils;
import com.ucon.newz.data.Articles;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.Sources;
import com.ucon.newz.data.local.NewzDBContract;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/9/17.
 */

public class NewzRemoteDataRepository implements NewsDataRepository.NewsDataRemote {

    private static NewzRemoteDataRepository INSTANCE;

    private Context mContext;

    private NewzRemoteDataRepository(Context context){
        mContext = context;
    }

    public static NewzRemoteDataRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new NewzRemoteDataRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull final NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        getSourceNotOverride(loadSourceCallback);
    }

    @Override
    public void getArticles(@NonNull final NewsDataRepository.LoadArticlesCallback loadSourceCallback, final String sourceID, final String sortby) throws IOException, JSONException {
        getArticleesNotOverride(loadSourceCallback, sourceID, sortby);
    }


    @Override
    public void getSourcesRemoteOnly(@NonNull NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        getSourceNotOverride(loadSourceCallback);
    }

    @Override
    public void getArticlesRemoteOnly(@NonNull NewsDataRepository.LoadArticlesCallback loadSourceCallback, String sourceID, String sortby) throws IOException, JSONException {

    }

    private void getSourceNotOverride(final NewsDataRepository.LoadSourceCallback loadSourceCallback){
        AsyncTask<Void, Void, ContentValues[]> asyncRemote = new AsyncTask<Void, Void, ContentValues[]>() {
            @Override
            protected ContentValues[] doInBackground(Void... voids) {
                String response = null;
                ContentValues[] sources = null;
                try {
                    response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrlSource());
                    sources = NewsJsonUtils.getSourcesFromJson(response);
                    Log.d("test data", response);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return sources;
            }

            @Override
            protected void onPostExecute(ContentValues[] contentValues) {

                if(contentValues != null){
                    mContext.getContentResolver().bulkInsert(
                            NewzDBContract.SourceEntry.CONTENT_URI,
                            contentValues);
                }


                Cursor cursor = mContext.getContentResolver().query(
                        NewzDBContract.SourceEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );


                try {
                    loadSourceCallback.OnTaskLoaded(Sources.getSourcesList(cursor));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncRemote.execute();
    }

    private void getArticleesNotOverride(final NewsDataRepository.LoadArticlesCallback loadSourceCallback, final String sourceID, final String sortby){
        AsyncTask<String, Void , ContentValues[]> a = new AsyncTask<String, Void, ContentValues[]>() {
            @Override
            protected ContentValues[] doInBackground(String... strings) {
                String response = null;
                ContentValues[] articles = null;

                try {
                    response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrlArticle(
                            strings[0]
                            ,strings[1]));
                    articles = NewsJsonUtils.getArticlesFromJson(response);

                    Log.d("test data articles", response);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return articles;
            }

            @Override
            protected void onPostExecute(ContentValues[] contentValues) {
                if(contentValues!=null){
                    mContext.getContentResolver().bulkInsert(
                            NewzDBContract.ArticlesEntry.CONTENT_URI,
                            contentValues);
                }


                Cursor cursor = mContext.getContentResolver().query(
                        NewzDBContract.ArticlesEntry.buildArticleUri(sourceID, sortby),
                        null,
                        null,
                        new String[]{sourceID},
                        sortby
                );

                try {
                    loadSourceCallback.OnTaskLoaded(Articles.getArticlesList(cursor, sourceID));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        a.execute(sourceID,sortby);
    }
}
