package com.ucon.newz.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucon.newz.data.local.NewzDBContract.SourceEntry;
import com.ucon.newz.data.local.NewzDBContract.ArticlesEntry;

/**
 * Created by saucon on 9/8/17.
 */

public class NewzDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "newz.db";

    private static final int DATABASE_VERSION = 4;

    public NewzDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SOURCE_TABLE =
                "CREATE TABLE " + SourceEntry.TABLE_NAME + " ( " +
                SourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SourceEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SourceEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                SourceEntry.COLUMN_SOURCE_ID + " TEXT NOT NULL, "+
                " UNIQUE ( " + SourceEntry.COLUMN_SOURCE_ID + " ) ON CONFLICT REPLACE );";

        final String SQL_CREATE_ARTICLE_TABLE =
                "CREATE TABLE " + ArticlesEntry.TABLE_NAME + " ( " +
                ArticlesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticlesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ArticlesEntry.COLUMN_DESC + " TEXT NOT NULL, "+
                ArticlesEntry.COLUMN_URL + " TEXT NOT NULL, "+
                ArticlesEntry.COLUMN_IMAGE + " TEXT NOT NULL, "+
                ArticlesEntry.COLUMN_DATE + " INTEGER NOT NULL, "+
                ArticlesEntry.COLUMN_SOURCE_ID + " TEXT NOT NULL, "+
                " UNIQUE ( " + ArticlesEntry.COLUMN_URL + " ) ON CONFLICT REPLACE );";

        sqLiteDatabase.execSQL(SQL_CREATE_SOURCE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ARTICLE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SourceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
