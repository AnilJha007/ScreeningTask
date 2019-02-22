package com.wipro.screeningtask.exercise.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.database.ExerciseDatabase;
import com.wipro.screeningtask.databinding.ActivityExerciseBinding;
import com.wipro.screeningtask.exercise.adapter.ExerciseAdapter;
import com.wipro.screeningtask.network.ApiClient;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.utils.ConstantUtil;
import com.wipro.screeningtask.utils.InternetUtil;
import com.wipro.screeningtask.utils.ViewModelFactory;

public class ExerciseActivity extends AppCompatActivity {

    private ActivityExerciseBinding exerciseBinding;
    private ExerciseAdapter exerciseAdapter;
    private ExerciseViewModel exerciseViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private InternetUtil internetUtil;
    private ExerciseDatabase exerciseDatabase;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exerciseBinding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);

        sharedPreferences = getSharedPreferences(ConstantUtil.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        internetUtil = new InternetUtil(this);
        exerciseDatabase = ExerciseDatabase.getInstance(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // setting toolbar
        toolbarSetup();

        // setting recycler view and attaching adapter to it
        recyclerViewSetup();

        // initialize view model using view model factory
        exerciseViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), new ExerciseRepository(getApplication(), editor, internetUtil, exerciseDatabase, apiInterface))).get(ExerciseViewModel.class);

        // observe data and set it into recycler view
        observeViewModelData(false);

        // setting pull to refresh for updated data
        pullToRefreshSetup();

    }

    private void pullToRefreshSetup() {

        exerciseBinding.swipeRefreshLayout.setOnRefreshListener(() -> observeViewModelData(true));
    }

    private void observeViewModelData(boolean isFromPullRefresh) {

        // observing data from api call or room database
        exerciseViewModel.getExerciseList(isFromPullRefresh).observe(this, exercises -> {

            exerciseAdapter.setData(exercises);

            if (exerciseBinding.swipeRefreshLayout.isRefreshing())
                exerciseBinding.swipeRefreshLayout.setRefreshing(false);

            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(sharedPreferences.getString(ConstantUtil.TOOLBAR_TITLE, ""));

        });

        // observing loading state here
        exerciseViewModel.getLoadingState().observe(this, isLoading -> {

            if (isLoading != null && isLoading) {
                exerciseBinding.textViewLoading.setVisibility(View.VISIBLE);
            } else {
                exerciseBinding.textViewLoading.setVisibility(View.GONE);
            }
        });

        // observing error message here
        exerciseViewModel.getErrorMessage().observe(this, s -> {

            if (exerciseBinding.swipeRefreshLayout.isRefreshing()) {
                exerciseBinding.swipeRefreshLayout.setRefreshing(false);
            }

            Snackbar.make(exerciseBinding.rootLayout, s, 3000).show();
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
        setSupportActionBar(exerciseBinding.toolbar.toolbarCommon);
    }
}
