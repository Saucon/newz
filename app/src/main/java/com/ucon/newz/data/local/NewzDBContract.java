package com.ucon.newz.data.local;

import android.net.Uri;
import android.provider.BaseColumns;



/**
 * Created by saucon on 9/7/17.
 */

public class NewzDBContract {

    public static final String CONTENT_AUTHORITY = "com.ucon.newz";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SOURCES = "sources";
    public static final String PATH_ARTICLES = "articles";


    private NewzDBContract(){}

    public static final class SourceEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SOURCES)
                .build();

        public static String TABLE_NAME = "sources";

        public static String COLUMN_SOURCE_ID = "source_id";
        public static String COLUMN_NAME = "name";
        public static String COLUMN_DESC = "description";
    }

    public static final class ArticlesEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ARTICLES)
                .build();

        public static String TABLE_NAME = "articles";

        public static String COLUMN_AUTHOR = "author";
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_DESC = "description";
        public static String COLUMN_URL = "url";
        public static String COLUMN_IMAGE = "urlToImage";
        public static String COLUMN_DATE = "publishedAt";
        public static String COLUMN_SOURCE_ID = "source_id";

        public static Uri buildArticleUri(String sourceId, String sortBy){
            return CONTENT_URI.buildUpon()
                    .appendPath(sourceId)
                    .appendPath(sortBy)
                    .build();
        }
        public static Uri buildArticleUriWithTitle(String sourceId, String sortBy, String title){
            return CONTENT_URI.buildUpon()
                    .appendPath(sourceId)
                    .appendPath(sortBy)
                    .appendPath(title)
                    .build();
        }
    }
}
