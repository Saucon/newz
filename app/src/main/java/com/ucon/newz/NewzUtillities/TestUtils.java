package com.ucon.newz.NewzUtillities;

import android.content.ContentValues;
import android.content.Context;

import com.ucon.newz.data.local.NewzDBContract;

/**
 * Created by saucon on 9/8/17.
 */

public class TestUtils {

    private static final String[] idSource = {"abc-news-au","al-jazeera-english","ars-technica"};
    private static final String[] nameSource = {"ABC News (AU)","Al Jazeera English","Ars Technica"};
    private static final String[] descSource = {"A","B","C"};


    private static ContentValues createTestSourceContentValue(String id, String name, String desc){
        ContentValues sourceContentValues = new ContentValues();
        sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_SOURCE_ID, id);
        sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_NAME, name);
        sourceContentValues.put(NewzDBContract.SourceEntry.COLUMN_DESC, desc);

        return sourceContentValues;
    }

    public static void insertFakeData (Context context){
        ContentValues[] contentValuesArr = new ContentValues[idSource.length];
        for (int i = 0; i < idSource.length; i++){
            contentValuesArr[i] = createTestSourceContentValue(
                    idSource[i],
                    nameSource[i],
                    descSource[i]);
        }

        context.getContentResolver().bulkInsert(
                NewzDBContract.SourceEntry.CONTENT_URI,
                contentValuesArr);
    }
}
