package com.example.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.activities.MainActivity;
import com.example.bakingapp.fragments.RecipeFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction().add(R.id.recipe_fragment, new RecipeFragment()).commit();
    }

    @Test
    public void displayNutellaPie() {
      onData(anything()).inAdapterView(withId(R.id.rv_recipes)).atPosition(0).perform(click());

      onView(withId(R.id.recipe_name_tv)).check(matches(withText("Nutella Pie")));
    }
}
