package com.ucon.newz.NewsSources.Presentation;

import com.ucon.newz.BasePresenter;
import com.ucon.newz.BaseView;
import com.ucon.newz.NewsSources.domain.model.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 9/7/17.
 */

public interface SourcesContract {
    interface View extends BaseView<Presenter>{
        void loadSourceView(List<Sources> cursor);
        void stopRefreshview();
    }

    interface Presenter extends BasePresenter{
        void loadSources() throws IOException, JSONException;
        void loadSourcesRemote() throws IOException, JSONException;
    }
}
