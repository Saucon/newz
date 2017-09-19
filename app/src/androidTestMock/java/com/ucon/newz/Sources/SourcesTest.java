package com.ucon.newz.Sources;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.ucon.newz.data.FakeLocalDatsImpl;
import com.ucon.newz.NewsSources.SourcesActivity;
import com.ucon.newz.R;
import com.ucon.newz.data.Sources;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by saucon on 9/17/17.
 */

public class SourcesTest {

    public static final Sources SOURCES = new Sources(1,"A","A","A");

    @Rule
    public ActivityTestRule<SourcesActivity> mSourcesActivityTestRule =
            new ActivityTestRule<>(SourcesActivity.class);

    @Before
    public void intentWithStubbedNoteId() {
        // Add a note stub to the fake service api layer.
        FakeLocalDatsImpl.addSources(SOURCES);

        Intent startIntent = new Intent();
        mSourcesActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void AddSourceToList(){

//
        onView(withId(R.id.rv_sources)).perform(
                scrollTo(hasDescendant(withText("A"))));
    }
}
