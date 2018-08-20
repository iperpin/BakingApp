package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.activities.MainActivity;
import com.example.bakingapp.fragments.RecipeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesActivityBasicTest {

    SharedPreferences.Editor editor;
    Context targetContext;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false); // Activity is not launched immediately

    @Before
    public void setUp() {
        targetContext = getInstrumentation().getTargetContext();
        editor = PreferenceManager
                .getDefaultSharedPreferences(targetContext).edit();
        editor.putString(targetContext.getString(R.string.ingredients), "");
        editor.commit();
    }

    @Test
    public void checkVisibilityOfNoInternetTextView() {

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.tv_no_internet)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void checkVisibilityOfReciclerView() {

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.rv_recipes)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }



    @After
    public void removePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(targetContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
