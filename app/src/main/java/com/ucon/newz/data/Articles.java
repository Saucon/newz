package com.ucon.newz.data;

import android.database.Cursor;

import com.ucon.newz.data.local.NewzDBContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/14/17.
 */

public class Articles {

    private int _ID;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private long publishedAt;
    private String sourceId;

    public Articles(int _ID, String title,String description, String url, String urlToImage, long publishedAt, String sourceId){
        this._ID = _ID;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.sourceId = sourceId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }


    public static List<Articles> getArticlesList(Cursor cursor, String sourceID){
        List<Articles> articlesList = new ArrayList<>();

        if(cursor!=null || cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int _ID = cursor.getInt(cursor.getColumnIndex(NewzDBContract.ArticlesEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_DESC));
                String url = cursor.getString(cursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_URL));
                String urlToImage = cursor.getString(cursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_IMAGE));
                long publishedAt = cursor.getLong(cursor.getColumnIndex(NewzDBContract.ArticlesEntry.COLUMN_DATE));


                Articles articles = new Articles(_ID,
                        title,
                        description,
                        url,
                        urlToImage,
                        publishedAt,
                        sourceID
                );

                articlesList.add(articles);

            }
            cursor.close();
        }
        return articlesList;
    }
}
