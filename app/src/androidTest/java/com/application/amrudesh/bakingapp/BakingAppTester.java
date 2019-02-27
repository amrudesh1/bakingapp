package com.application.amrudesh.bakingapp;



import com.application.amrudesh.bakingapp.RecyclerViewMatcher.RecyclerViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class BakingAppTester {
    CountingIdlingResource countingIdlingResource;
    @Rule
    public ActivityTestRule<com.application.amrudesh.bakingapp.Activities.MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(com.application.amrudesh.bakingapp.Activities.MainActivity.class);


    @Before
    public void useAppContext() {

        countingIdlingResource=mainActivityActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    @Test
    public void RecyclerViewTester() {
        //Checking First Items
        Espresso.onView(withRecyclerView(R.id.recipesList).atPositionOnView(0, R.id.recipe_name))
                .check(matches(withText("Nutella Pie")));

        //Click on 0th Position
        Espresso.onView(withId(R.id.recipesList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.ingredientList)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.stepsList)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.stepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        Espresso.onView(withId(R.id.description)).check(matches(isDisplayed()));

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @After
    public void unregisterIdlingResource()
    {
        if (countingIdlingResource != null)
        {
            IdlingRegistry.getInstance().unregister(countingIdlingResource);
        }
    }
}