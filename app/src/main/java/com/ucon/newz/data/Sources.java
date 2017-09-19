package com.ucon.newz.data;

import android.database.Cursor;

import com.ucon.newz.data.local.NewzDBContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saucon on 9/14/17.
 */

public class Sources {
    private int _ID;
    private String name;
    private String desc;
    private String sourceId;

    Sources(){}

    public Sources(int _ID, String name, String desc, String sourceId){
        this._ID = _ID;
        this.name = name;
        this.desc = desc;
        this.sourceId = sourceId;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public static List<Sources> getSourcesList(Cursor cursor){
        List<Sources> sourcesList = new ArrayList<>();
        if(cursor!=null || cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int _ID = cursor.getInt(cursor.getColumnIndex(NewzDBContract.SourceEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_DESC));
                String sourceId = cursor.getString(cursor.getColumnIndex(NewzDBContract.SourceEntry.COLUMN_SOURCE_ID));
                Sources sources = new Sources(_ID, name, desc, sourceId);
                sourcesList.add(sources);
            }
            cursor.close();
        }
        return sourcesList;
    }
}
