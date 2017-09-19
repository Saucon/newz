package com.ucon.newz.data;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ucon.newz.NewzUtillities.TestUtils;
import com.ucon.newz.data.local.NewzDBContract;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by saucon on 9/8/17.
 */

@RunWith(AndroidJUnit4.class)
public class contentProviderTest {

    @Test
    public void insertFakeData(){
        Context context = InstrumentationRegistry.getContext();
        TestUtils.insertFakeData(context);

        Cursor cursor = context.getContentResolver().query(
                NewzDBContract.SourceEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(3,cursor.getCount());
    }

    @Test
    public void deleteFakeData(){
        Context context = InstrumentationRegistry.getContext();

        int row = context.getContentResolver().delete(
                NewzDBContract.SourceEntry.CONTENT_URI,
                null,
                null);

        assertEquals(3,row);

        Cursor cursor = context.getContentResolver().query(
                NewzDBContract.SourceEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(0,cursor.getCount());
    }

    @Test
    public void getArticlesCount(){
        Context context = InstrumentationRegistry.getContext();
        TestUtils.insertFakeData(context);

        Cursor cursor = context.getContentResolver().query(
                NewzDBContract.ArticlesEntry.buildArticleUri("abc-news-au","top"),
                null,
                null,
                null,
                null
        );

        assertEquals(0,cursor.getCount());
    }


}
