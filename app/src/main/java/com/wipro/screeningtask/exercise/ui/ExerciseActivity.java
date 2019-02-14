package com.wipro.screeningtask.exercise.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.databinding.ActivityExerciseBinding;
import com.wipro.screeningtask.exercise.adapter.ExerciseAdapter;
import com.wipro.screeningtask.exercise.pojo.Exercise;
import com.wipro.screeningtask.utils.ConstantUtil;
import com.wipro.screeningtask.utils.ViewModelFactory;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private ActivityExerciseBinding exerciseBinding;
    private ExerciseAdapter exerciseAdapter;
    private ExerciseViewModel exerciseViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exerciseBinding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        sharedPreferences = getSharedPreferences(ConstantUtil.SHARED_PREF, Context.MODE_PRIVATE);

        // setting toolbar
        toolbarSetup();

        // setting recycler view and attaching adapter to it
        recyclerViewSetup();

        // initialize view model using view model factory
        exerciseViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), new ExerciseRepository(getApplication()))).get(ExerciseViewModel.class);

        // observe data and set it into recycler view
        observeData();

    }

    private void observeData() {

        exerciseViewModel.getExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exerciseAdapter.setData(exercises);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(sharedPreferences.getString(ConstantUtil.TOOLBAR_TITLE, ""));
            }
        });
    }

    private void recyclerViewSetup() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.bg_recycler_separator));
        exerciseBinding.recyclerViewExercise.addItemDecoration(dividerItemDecoration);

        exerciseAdapter = new ExerciseAdapter();
        exerciseBinding.recyclerViewExercise.setAdapter(exerciseAdapter);
    }

    private void toolbarSetup() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_common));
    }
}
