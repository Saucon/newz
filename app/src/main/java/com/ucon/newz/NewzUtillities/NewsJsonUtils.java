package com.ucon.newz.NewzUtillities;

import android.content.ContentValues;

import com.ucon.newz.data.local.NewzDBContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by saucon on 9/7/17.
 */

public class NewsJsonUtils {

    /*PARAMS*/
    //sources json
    private static String SOURCE_ID = "id";
    private static String SOURCE_NAME = "name";
    private static String SOURCE_DESC = "description";

    private static String SOURCE_DATA = "sources";
    private static String SOURCE_STATUS = "sources";

    //articles json
    private static String ARTICLE_AUTHOR = "author";
    private static String ARIICLE_TITLE = "title";
    private static String ARTICLE_DESC = "description";
    private static String ARTICLE_URL = "url";
    private static String ARTICLE_IMAGE = "urlToImage";
    private static String ARTICLE_DATE = "publishedAt";

    private static String ARTICLE_DATA = "articles";
    private static String ARTICLE_SOURCE = "source";
    private static String ARTICLE_SORTBY = "sortBy";
    private static String ARTICLE_STATUS = "status";

    //status
    private static String STATUS = "status";
    private static String CODE = "code";
    private static String MESSAGE = "message";

    /*ERROR CODE*/
    //error code
    public static String SOURCE_UNAVAILABLE_SORTED_BY = "sourceUnavailableSortedBy";

    /*SORT CODE*/
    public static String SORT_DEFAULT = "top";

    public static ContentValues[] getSourcesFromJson(String jsonSource) throws JSONException{
        JSONObject sourceDataJson = new JSONObject(jsonSource);

        String status = cekJsonStatus(jsonSource);
        if(status == null || status.isEmpty() || !status.equals("ok")){
            return null;
        }

        JSONArray sourcesArrJson = sourceDataJson.getJSONArray(SOURCE_DATA);

        ContentValues[] sourcesArrContentValues = new ContentValues[sourcesArrJson.length()];

        for(int i = 0; i < sourcesArrJson.length(); i++){

            JSONObject sourceJson = sourcesArrJson.getJSONObject(i);

            String id = sourceJson.getString(SOURCE_ID);
            String name = sourceJson.getString(SOURCE_NAME);
            String desc = sourceJson.getString(SOURCE_DESC);

            ContentValues sourceContentValues =  new ContentValues();

            sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_SOURCE_ID, id);
            sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_NAME, name);
            sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_DESC, desc);

            sourcesArrContentValues[i] = sourceContentValues;

        }


        return sourcesArrContentValues;
    }

    public static ContentValues[] getArticlesFromJson (String jsonArticles) throws JSONException{
        JSONObject articleDataJson = new JSONObject(jsonArticles);


        String status = cekJsonStatus(jsonArticles);
        if(status == null || status.isEmpty() || !status.equals("ok")){
            return null;
        }

        String sourceID = articleDataJson.getString(ARTICLE_SOURCE);

        JSONArray articleArrJson = articleDataJson.getJSONArray(ARTICLE_DATA);

        ContentValues[] articlesArrContentValues = new ContentValues[articleArrJson.length()];

        for(int i = 0; i < articleArrJson.length(); i++){
            JSONObject articleJson = articleArrJson.getJSONObject(i);

            String title = articleJson.getString(ARIICLE_TITLE);
            String desc = articleJson.getString(ARTICLE_DESC);
            String url = articleJson.getString(ARTICLE_URL);
            String image = articleJson.getString(ARTICLE_IMAGE);

            String date = articleJson.getString(ARTICLE_DATE);
            long dateinmilis = 0;
            try {
                if(date!=null){
                    dateinmilis = NewzDateUtil.getDateFromString(date).getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ContentValues articleContentValues = new ContentValues();
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_TITLE, title);
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_DESC, desc);
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_URL, url);
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_IMAGE, image);
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_SOURCE_ID, sourceID);
            articleContentValues.put(NewzDBContract.ArticlesEntry.COLUMN_DATE, dateinmilis);

            articlesArrContentValues[i] = articleContentValues;

        }

        return articlesArrContentValues;
    }

    public static String cekJsonStatus(String jsonStatString) throws JSONException {
        JSONObject statJsonObject = new JSONObject(jsonStatString);
        if(statJsonObject.has(STATUS)){
            String status = statJsonObject.getString(STATUS);

            if(!status.equals("ok")){
                String code = statJsonObject.getString(CODE);
                return code;
            }else{return status;}
        }else{return null;}
    }
}


























