package com.ucon.newz;

/**
 * Created by saucon on 9/14/17.
 */

import com.ucon.newz.NewsSources.SourcesContract;
import com.ucon.newz.NewsSources.SourcesPresenter;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;
import com.ucon.newz.data.Sources;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Lists;

public class SourcesPresenterTest {

    private static final List<Sources> SOURCES = Lists.newArrayList(new Sources(1,"aa","aa","aaa"),new Sources(1,"aa","aa","aaa"));
    @Mock
    private NewzRepository mNewzReposiory;

    @Mock
    private SourcesContract.View mSourcesView;

    @Captor
    private ArgumentCaptor<NewsDataRepository.LoadSourceCallback> mLoadSourceCallbackCaptor;

    private SourcesPresenter mSourcesPresenter;

    @Before
    public void setupSourcesPresenter(){



        MockitoAnnotations.initMocks(this);

        mSourcesPresenter = new SourcesPresenter(mNewzReposiory, mSourcesView);

        // Use your the mergeCursor as you would use your cursor.

    }

    @Test
    public void loadSourceTest(){

        try {
            mSourcesPresenter.loadSources();

            verify(mNewzReposiory).getSources(mLoadSourceCallbackCaptor.capture());
            mLoadSourceCallbackCaptor.getValue().OnTaskLoaded(SOURCES);

            verify(mSourcesView).loadSourceView(SOURCES);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
