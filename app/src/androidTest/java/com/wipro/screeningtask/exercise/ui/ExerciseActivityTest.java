package com.wipro.screeningtask.exercise.ui;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.wipro.screeningtask.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExerciseActivityTest {

    @Rule
    public ActivityTestRule<ExerciseActivity> activityTestRule = new ActivityTestRule<ExerciseActivity>(ExerciseActivity.class);
    private ExerciseActivity exerciseActivity;

    @Before
    public void setUp() throws Exception {
        exerciseActivity = activityTestRule.getActivity();
    }

    @Test
    public void launchExerciseActivityTest() {

        View view = exerciseActivity.findViewById(R.id.rootLayout);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        exerciseActivity = null;
    }
}