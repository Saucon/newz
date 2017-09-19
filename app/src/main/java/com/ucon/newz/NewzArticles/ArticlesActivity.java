package com.ucon.newz.NewzArticles;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ucon.newz.Injection;
import com.ucon.newz.R;

import org.json.JSONException;

import java.io.IOException;

public class ArticlesActivity extends AppCompatActivity implements ArticlesContract.View, ArticlesAdapter.ArticlesAdapterOnClickHandler {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;

    RecyclerView mArticlesRecyclerView;
    ArticlesAdapter mArticlesAdapter;

    private ArticlesPresenter mArticlesPresenter;

    private final String DEFAULT_SORT_BY = "top";

    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        getSupportActionBar().setTitle(getString(R.string.title_article_activity));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);

        mArticlesRecyclerView = (RecyclerView) findViewById(R.id.rv_articles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mArticlesRecyclerView.setLayoutManager(linearLayoutManager);

        mArticlesAdapter = new ArticlesAdapter(this, this);

        String sourceid = getIntent().getStringExtra("sourceId");

        initPresenter();

        mArticlesPresenter.getLoadArticlesParams(sourceid,DEFAULT_SORT_BY);
        try {
            mArticlesPresenter.loadArticles();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        searchEditText = (EditText) findViewById(R.id.et_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mArticlesPresenter.getTitleSearch(searchEditText.getText().toString());
                try {
                    mArticlesPresenter.loadArticlesFromSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                };
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mArticlesPresenter.loadArticlesRemote();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(!prefs.getBoolean("first_time_article",false)){
            showSnackNoCon();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time_article", true);
            editor.apply();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {

            mArticlesPresenter.getTitleSearch(searchEditText.getText().toString());
            try {
                mArticlesPresenter.loadArticlesFromSearch();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setPresenter(ArticlesContract.Presenter presenter) {
        if(presenter == null){
            throw new NullPointerException();
        }
        mArticlesPresenter = (ArticlesPresenter) presenter;
    }

    @Override
    public void loadArticlesView(Cursor cursor) {
        Log.d("test cursor", cursor.getCount()+"");
        progressBar.setVisibility(View.GONE);
        mArticlesAdapter.swapCursor(cursor);
        mArticlesRecyclerView.setAdapter(mArticlesAdapter);
    }

    @Override
    public void stopRefreshView() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onClick(String url) {
        if(url!=null){

            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://" + url;
            }
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary));
            builder.setSecondaryToolbarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl((AppCompatActivity)this, Uri.parse(url));
        }
    }


    private void initPresenter(){
        mArticlesPresenter = new ArticlesPresenter(Injection.provideRepository(this), this);
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
