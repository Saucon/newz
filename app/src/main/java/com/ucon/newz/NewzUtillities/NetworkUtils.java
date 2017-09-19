package com.ucon.newz.NewzUtillities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by saucon on 9/7/17.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String ARTICLE_ENDPOINT_URL =
            "https://newsapi.org/v1/articles";
    private static final String SOURCE_ENDPOINT_URL =
            "https://newsapi.org/v1/sources";

    private static final String API_KEY = "7da63fb8e2f84e2581fd50571dde9eca";

    //param articles
    private static final String PARAM_ARTICLE_SOURCE = "source";
    private static final String PARAM_ARTICLE_API_KEY = "apiKey";
    private static final String PARAM_ARTICLE_SORT_BY = "sortBy";


    public static URL buildUrlArticle(String source, String sortBy){
        Uri uriArticle = Uri.parse(ARTICLE_ENDPOINT_URL).buildUpon()
                .appendQueryParameter(PARAM_ARTICLE_SOURCE, source)
                .appendQueryParameter(PARAM_ARTICLE_SORT_BY, sortBy)
                .appendQueryParameter(PARAM_ARTICLE_API_KEY, API_KEY)
                .build();

        try {
            URL articleUrl = new URL(uriArticle.toString());
            Log.v(TAG, "URL: " + articleUrl);
            return articleUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL buildUrlSource(){
        Uri uriArticle = Uri.parse(SOURCE_ENDPOINT_URL).buildUpon()
                .build();

        try {
            URL sourceUrl = new URL(uriArticle.toString());
            Log.v(TAG, "URL: " + sourceUrl);
            return sourceUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String getResponseFromHttpUrl (URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
