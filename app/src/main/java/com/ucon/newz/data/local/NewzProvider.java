package com.ucon.newz.data.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ucon.newz.NewzUtillities.NetworkUtils;

/**
 * Created by saucon on 9/8/17.
 */

public class NewzProvider extends ContentProvider {

    public static final int CODE_SOURCES = 100;

    public static final int CODE_ARTICLES = 200;
    public static final int CODE_ARTICLES_WITH_SOURCEID = 201;
    public static final int CODE_ARTICLES_WITH_SEARCH = 202;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NewzDBHelper mNewzDBHelper;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = NewzDBContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NewzDBContract.PATH_SOURCES, CODE_SOURCES);

        matcher.addURI(authority, NewzDBContract.PATH_ARTICLES, CODE_ARTICLES);

        matcher.addURI(authority, NewzDBContract.PATH_ARTICLES+"/*/*", CODE_ARTICLES_WITH_SOURCEID);
        matcher.addURI(authority, NewzDBContract.PATH_ARTICLES+"/*/*/*", CODE_ARTICLES_WITH_SEARCH);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mNewzDBHelper = new NewzDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projections, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){

            case CODE_SOURCES:
                cursor = mNewzDBHelper.getReadableDatabase().query(
                        NewzDBContract.SourceEntry.TABLE_NAME,
                        projections,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CODE_ARTICLES_WITH_SOURCEID:
                cursor = mNewzDBHelper.getReadableDatabase().query(
                        NewzDBContract.ArticlesEntry.TABLE_NAME,
                        projections,
                        NewzDBContract.ArticlesEntry.COLUMN_SOURCE_ID + " = ? ",
                        selectionArgs,
                        null,
                        null,
                        NewzDBContract.ArticlesEntry.COLUMN_DATE + " DESC"
                );
                break;

            case CODE_ARTICLES_WITH_SEARCH:
                cursor = mNewzDBHelper.getReadableDatabase().query(
                        NewzDBContract.ArticlesEntry.TABLE_NAME,
                        projections,
                        NewzDBContract.ArticlesEntry.COLUMN_SOURCE_ID + " = ? and " +
                        NewzDBContract.ArticlesEntry.COLUMN_TITLE + " LIKE ? "
                        ,
                        selectionArgs,
                        null,
                        null,
                        NewzDBContract.ArticlesEntry.COLUMN_DATE + " DESC"
                );
                break;
            default:
                throw  new UnsupportedOperationException("Unknown Uri : "+uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mNewzDBHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case CODE_SOURCES:
                db.beginTransaction();
                int rowsInserted = 0;
                try{
                    for (ContentValues value : values){
                        long _id = db.insert(NewzDBContract.SourceEntry.TABLE_NAME, null, value);

                        if(_id != -1){
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_ARTICLES:
                db.beginTransaction();
                int rowsInserted1 = 0;
                try{
                    for (ContentValues value : values){
                        long _id = db.insert(NewzDBContract.ArticlesEntry.TABLE_NAME, null, value);

                        if(_id != -1){
                            rowsInserted1++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted1 > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted1;
            default:
                return super.bulkInsert(uri, values);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int numRowDeleted = 0;

        if(null == s){
            s = "1";
        }

        switch (sUriMatcher.match(uri)){
            case CODE_SOURCES:
                numRowDeleted = mNewzDBHelper.getWritableDatabase().delete(
                        NewzDBContract.SourceEntry.TABLE_NAME,
                        s,
                        strings
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : "+ uri);
        }

        if(numRowDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
