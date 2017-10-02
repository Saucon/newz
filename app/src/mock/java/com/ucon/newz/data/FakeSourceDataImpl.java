package com.ucon.newz.data;

import android.support.annotation.NonNull;

import com.ucon.newz.NewsSources.domain.model.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/18/17.
 */

public class FakeSourceDataImpl implements NewsDataRepository.NewsDataRemote {


    private static FakeSourceDataImpl INSTANCE = null;

    public static List<Sources> SOURCES_FAKE = new ArrayList<>();

    public static FakeSourceDataImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FakeSourceDataImpl();
        }

        return INSTANCE;
    }

    @Override
    public void getArticles(@NonNull NewsDataRepository.LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException {

    }

    @Override
    public void getSources(@NonNull NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {
        loadSourceCallback.OnTaskLoaded(SOURCES_FAKE);
    }

    @Override
    public void getArticlesRemoteOnly(@NonNull NewsDataRepository.LoadArticlesCallback loadArticlesCallback, String sourceID, String sortby) throws IOException, JSONException {

    }

    @Override
    public void getSourcesRemoteOnly(@NonNull NewsDataRepository.LoadSourceCallback loadSourceCallback) throws IOException, JSONException {

    }
}
