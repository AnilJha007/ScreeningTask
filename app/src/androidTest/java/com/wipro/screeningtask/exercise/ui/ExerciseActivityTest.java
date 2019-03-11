package com.wipro.screeningtask.exercise.ui;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wipro.screeningtask.R;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

public class ExerciseActivityTest {


    @Rule
    public ActivityTestRule<ExerciseActivity> activityTestRule = new ActivityTestRule<ExerciseActivity>(ExerciseActivity.class);
    private ExerciseActivity exerciseActivity;

    @Before
    public void setUp() throws Exception {
        exerciseActivity = activityTestRule.getActivity();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void launchExerciseActivityTest() {

        View view = exerciseActivity.findViewById(R.id.rootLayout);
        assertNotNull(view);
    }

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