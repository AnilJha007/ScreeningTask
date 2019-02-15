package com.wipro.screeningtask.exercise.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.databinding.ActivityExerciseBinding;
import com.wipro.screeningtask.exercise.adapter.ExerciseAdapter;
import com.wipro.screeningtask.exercise.pojo.Exercise;
import com.wipro.screeningtask.utils.ConstantUtil;
import com.wipro.screeningtask.utils.InternetUtil;
import com.wipro.screeningtask.utils.ViewModelFactory;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class ExerciseActivity extends AppCompatActivity {

    private ActivityExerciseBinding exerciseBinding;
    private ExerciseAdapter exerciseAdapter;
    private ExerciseViewModel exerciseViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private InternetUtil internetUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exerciseBinding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        sharedPreferences = getSharedPreferences(ConstantUtil.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        internetUtil = new InternetUtil(this);

        // setting toolbar
        toolbarSetup();

        // setting recycler view and attaching adapter to it
        recyclerViewSetup();

        // initialize view model using view model factory
        exerciseViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), new ExerciseRepository(getApplication(), editor, internetUtil))).get(ExerciseViewModel.class);

        // observe data and set it into recycler view
        observeViewModelData();

        // setting pull to update data
        pullToRefreshSetup();

    }

    private void pullToRefreshSetup() {

        exerciseBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                observeUpdatedViewModelData();
            }
        });
    }

    private void observeUpdatedViewModelData() {

        // observing data from api call
        exerciseViewModel.getUpdatedExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exerciseAdapter.setData(exercises);

                if (exerciseBinding.swipeRefreshLayout.isRefreshing())
                    exerciseBinding.swipeRefreshLayout.setRefreshing(false);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(sharedPreferences.getString(ConstantUtil.TOOLBAR_TITLE, ""));
            }
        });
    }

    private void observeViewModelData() {

        // observing data from api call
        exerciseViewModel.getExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exerciseAdapter.setData(exercises);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(sharedPreferences.getString(ConstantUtil.TOOLBAR_TITLE, ""));
            }
        });

        // observing loading state here
        exerciseViewModel.getLoadingState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {

                if (isLoading != null && isLoading) {
                    exerciseBinding.textViewLoading.setVisibility(View.VISIBLE);
                } else {
                    exerciseBinding.textViewLoading.setVisibility(View.GONE);
                }
            }
        });

        // observing error message here
        exerciseViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (exerciseBinding.swipeRefreshLayout.isRefreshing()) {
                    exerciseBinding.swipeRefreshLayout.setRefreshing(false);
                }

                Snackbar.make(exerciseBinding.rootLayout, s, 3000).show();
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
