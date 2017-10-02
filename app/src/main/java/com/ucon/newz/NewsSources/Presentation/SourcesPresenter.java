package com.ucon.newz.NewsSources.Presentation;


import android.support.annotation.NonNull;

import com.ucon.newz.NewsSources.domain.UseCase.GetSources;
import com.ucon.newz.UseCase;
import com.ucon.newz.UseCaseHandler;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;
import com.ucon.newz.NewsSources.domain.model.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by saucon on 9/7/17.
 */

public class SourcesPresenter implements SourcesContract.Presenter {

    //will delete this
//    private final NewzRepository mNewzRepository;

    private final SourcesContract.View mSourceView;
    //use case
    private final GetSources mGetSources;

    //use case handler
    private final UseCaseHandler mUseCaseHandler;


    public SourcesPresenter(@NonNull SourcesContract.View sourceView,
                            @NonNull GetSources getSources,
                            @NonNull UseCaseHandler useCaseHandler
                            ){
//        mNewzRepository = (NewzRepository) newzRepository;
        mSourceView = sourceView;

        mGetSources = checkNotNull(getSources, "GetSources cant be null");

        mUseCaseHandler = checkNotNull(useCaseHandler, "UseCaseHandler cant be null");

        mSourceView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadSources() throws IOException, JSONException {

        mUseCaseHandler.execute(mGetSources,
                new GetSources.RequestValues(),
                new UseCase.UseCaseCallback<GetSources.ResponseValues>() {
                    @Override
                    public void onSuccess(GetSources.ResponseValues response) {
                        mSourceView.loadSourceView(response.getSources());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void loadSourcesRemote() throws IOException, JSONException {
//        mNewzRepository.getSourcesRemoteOnly(new NewsDataRepository.LoadSourceCallback() {
//            @Override
//            public void OnTaskLoaded(List<Sources> cursor) throws IOException, JSONException {
//                mSourceView.loadSourceView(cursor);
//                mSourceView.stopRefreshview();
//            }
//        });
    }


}
