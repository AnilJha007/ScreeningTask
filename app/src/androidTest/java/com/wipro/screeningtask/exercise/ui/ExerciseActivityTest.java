package com.wipro.screeningtask.exercise.ui;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wipro.screeningtask.R;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class ExerciseActivityTest {

    private MockWebServer webServer;

    @Rule
    public ActivityTestRule<ExerciseActivity> activityTestRule = new ActivityTestRule<ExerciseActivity>(ExerciseActivity.class);
    private ExerciseActivity exerciseActivity;

    @Before
    public void setUp() throws Exception {
        exerciseActivity = activityTestRule.getActivity();
        MockitoAnnotations.initMocks(this);

        webServer = new MockWebServer();
        webServer.start(8080);
    }


    @Test
    public void testApiSuccessAndVerifyTitle() {

        activityTestRule.launchActivity(new Intent());

        Espresso.onView(withId(R.id.textViewLoading)).check(matches(not(isDisplayed())));
        Espresso.onView(withId(R.id.recyclerViewExercise)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("About Canada"))));

    }

   /* @Test
    public void launchExerciseActivityTest() {

        View view = exerciseActivity.findViewById(R.id.rootLayout);
        assertNotNull(view);
    }*/

    @Test
    public void swipeToRefreshTest() {
        onView(withId(R.id.swipeRefreshLayout))
                .perform(withConstraints(swipeDown(), isDisplayingAtLeast(95)));
    }

    @Test
    public void scrollToBottomRecyclerViewTest() {

        RecyclerView recyclerView = exerciseActivity.findViewById(R.id.recyclerViewExercise);

        if (getRowViewCount(recyclerView) > 0) {
            onView(withId(R.id.recyclerViewExercise))
                    .perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1));
        }
    }

    @After
    public void tearDown() throws Exception {
        exerciseActivity = null;

        webServer.shutdown();

    }

    private int getRowViewCount(RecyclerView recyclerView) {
        return recyclerView.getAdapter().getItemCount();
    }

    public static ViewAction withConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}