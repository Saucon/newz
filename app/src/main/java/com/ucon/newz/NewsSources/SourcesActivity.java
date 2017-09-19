package com.ucon.newz.NewsSources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ucon.newz.Injection;
import com.ucon.newz.NewzArticles.ArticlesActivity;
import com.ucon.newz.R;
import com.ucon.newz.data.Sources;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public class SourcesActivity extends AppCompatActivity implements SourcesContract.View, SourcesAdapter.SourcesAdapterOnClickHandler {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;

    RecyclerView mSourcesRecyclerView;
    SourcesAdapter mSourcesAdapter;

    SourcesPresenter mSourcesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        getSupportActionBar().setTitle(getString(R.string.title_source_activity));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);

        mSourcesRecyclerView = (RecyclerView) findViewById(R.id.rv_sources);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSourcesRecyclerView.setLayoutManager(linearLayoutManager);

        mSourcesAdapter = new SourcesAdapter(this, this);

        initPresenter();

        try {
            mSourcesPresenter.loadSources();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mSourcesPresenter.loadSourcesRemote();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(!prefs.getBoolean("first_time",false)){
            showSnackNoCon();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time", true);
            editor.apply();

        }


    }



    @Override
    public void setPresenter(SourcesContract.Presenter presenter) {
        if(presenter == null){
            throw new NullPointerException();
        }
        mSourcesPresenter = (SourcesPresenter) presenter;
    }

    @Override
    public void loadSourceView(List<Sources> cursor) {
        progressBar.setVisibility(View.GONE);
        mSourcesAdapter.swapCursor(cursor);
        mSourcesRecyclerView.setAdapter(mSourcesAdapter);
    }

    @Override
    public void stopRefreshview() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(String sourceId) {
        Intent intent = new Intent(this, ArticlesActivity.class);
        intent.putExtra("sourceId", sourceId);
        startActivity(intent);
    }

    private void initPresenter(){
        mSourcesPresenter = new SourcesPresenter(Injection.provideRepository(this), this);
        mSourcesPresenter.start();
    }
    private void showSnackNoCon(){
        final Snackbar conSnackbar =  Snackbar.make(findViewById(R.id.coordinator), "Swipe down to refresh data",
                Snackbar.LENGTH_LONG);
        conSnackbar.show();
        conSnackbar.setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conSnackbar.dismiss();
            }
        });
    }
}
