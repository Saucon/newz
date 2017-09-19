package com.ucon.newz.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/17/17.
 */

public class FakeLocalDatsImpl implements NewsDataRepository.NewsDataLocal {

    private static FakeLocalDatsImpl INSTANCE = null;

    public static List<Sources> SOURCES_FAKE = new ArrayList<>();

    public static FakeLocalDatsImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FakeLocalDatsImpl();
        }

        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        loadSourceCallback.OnTaskLoaded(SOURCES_FAKE);
    }

    @Override
    public void getArticles(@NonNull NewsDataRepository.LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException {

    }

    @Override
    public void getArticlesFromSearch(@NonNull NewsDataRepository.LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby, String title) throws IOException, JSONException {

    }


    @VisibleForTesting
    public static void addSources(Sources... notes) {
        for (Sources note : notes) {
            SOURCES_FAKE.add(note);
        }
    }

}
