package com.ucon.newz.NewsSources;


import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;
import com.ucon.newz.data.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/7/17.
 */

public class SourcesPresenter implements SourcesContract.Presenter {

    private final NewzRepository mNewzRepository;

    private final SourcesContract.View mSourceView;


    public SourcesPresenter(NewsDataRepository newzRepository, SourcesContract.View sourceView){
        mNewzRepository = (NewzRepository) newzRepository;
        mSourceView = sourceView;


        mSourceView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadSources() throws IOException, JSONException {
        mNewzRepository.getSources(new NewsDataRepository.LoadSourceCallback() {
            @Override
            public void OnTaskLoaded(List<Sources> cursor) {
                mSourceView.loadSourceView(cursor);
            }
        });
    }

    @Override
    public void loadSourcesRemote() throws IOException, JSONException {
        mNewzRepository.getSourcesRemoteOnly(new NewsDataRepository.LoadSourceCallback() {
            @Override
            public void OnTaskLoaded(List<Sources> cursor) throws IOException, JSONException {
                mSourceView.loadSourceView(cursor);
                mSourceView.stopRefreshview();
            }
        });
    }


}
