package com.wipro.screeningtask.exercise.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.databinding.ActivityExerciseBinding;
import com.wipro.screeningtask.exercise.adapter.ExerciseAdapter;

public class ExerciseActivity extends AppCompatActivity {

    private ActivityExerciseBinding exerciseBinding;
    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exerciseBinding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);

        // setting toolbar
        toolbarSetup();

        // setting recycler view and attaching adapter to it
        recyclerViewSetup();

    }

    private void recyclerViewSetup() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.bg_recycler_separator));
        exerciseBinding.recyclerViewExercise.addItemDecoration(dividerItemDecoration);

        exerciseAdapter = new ExerciseAdapter();
        exerciseBinding.recyclerViewExercise.setAdapter(exerciseAdapter);
    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
    }
}
