package com.ucon.newz.NewzUtillities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saucon on 9/9/17.
 */

public class NewzDateUtil {

    public static Date getDateFromString(String date) throws ParseException {
        Log.d("date ", date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(date);
    }

    public static String getStringFromDate(long date) throws ParseException {
        Date date1 = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return sdf.format(date1);
    }
}
