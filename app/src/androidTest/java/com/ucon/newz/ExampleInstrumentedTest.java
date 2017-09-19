package com.ucon.newz;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.ucon.newz.NewzUtillities.NetworkUtils;
import com.ucon.newz.NewzUtillities.NewsJsonUtils;
import com.ucon.newz.data.local.NewzDBContract;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ucon.newz", appContext.getPackageName());
    }


    @Test
    public void testSourceNetworkUtil() throws IOException {
        String respon = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrlSource());
        Log.d("respon test", respon);
        assertNotNull(respon);
    }

    @Test
    public void testArticleNetworkUtil() throws IOException, JSONException {
        String respon = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrlArticle("abc-news-au","latest"));
        String status = NewsJsonUtils.cekJsonStatus(respon);
        Log.d("respon article test", status);
        assertEquals("sort tidak ada", NewsJsonUtils.SOURCE_UNAVAILABLE_SORTED_BY, status);
    }

    @Test
    public void testSegment() throws IOException, JSONException {
        Uri uri = NewzDBContract.ArticlesEntry.buildArticleUri("a","b");
        assertEquals("sort tidak ada", "a", uri.getPathSegments().get(1));
    }
}
