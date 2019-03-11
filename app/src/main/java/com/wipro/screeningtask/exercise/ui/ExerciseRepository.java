package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.database.ExerciseDatabase;
import com.wipro.screeningtask.database.entity.ExerciseDataEntity;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.utils.InternetUtil;
import com.wipro.screeningtask.utils.SchedulerProvider.BaseSchedulerProvider;

import io.reactivex.observers.DisposableObserver;

public class ExerciseRepository {

    private Application application;
    private InternetUtil internetUtil;
    private MutableLiveData<Boolean> mutableIsLoading;
    private MutableLiveData<String> mutableErrorData;

    private ExerciseDatabase exerciseDatabase;
    private ApiInterface apiInterface;
    private BaseSchedulerProvider schedulerProvider;

    public ExerciseRepository(Application application, InternetUtil internetUtil, ExerciseDatabase exerciseDatabase, ApiInterface apiInterface, BaseSchedulerProvider baseSchedulerProvider) {
        this.internetUtil = internetUtil;
        this.application = application;
        this.exerciseDatabase = exerciseDatabase;
        this.apiInterface = apiInterface;
        this.schedulerProvider = baseSchedulerProvider;

    }

    // get loading state
    public MutableLiveData<Boolean> getLoadingState() {
        return mutableIsLoading;
    }

    // get error state
    public MutableLiveData<String> getErrorMessage() {
        return mutableErrorData;
    }

    // get exercise list
    public LiveData<ExerciseDataEntity> getExerciseList(boolean isFromPullRefresh) {

        mutableErrorData = new MutableLiveData<>();
        mutableIsLoading = new MutableLiveData<>();

        // here we need updated data from server
        if (isFromPullRefresh) {

            callApiAndInsertIntoDB();

        } else {

            // check if already data available into database or not if yes then return else call api
            long rowCount = exerciseDatabase.exerciseDao().getExerciseCount();

            if (rowCount == 0) {

                mutableIsLoading.setValue(true);
                callApiAndInsertIntoDB();

            }
        }

        return exerciseDatabase.exerciseDao().getExercise();

    }

    private void callApiAndInsertIntoDB() {

        if (!internetUtil.isNetworkAvailable()) {
            mutableIsLoading.setValue(false);
            mutableErrorData.setValue(application.getResources().getString(R.string.internet_not_available));
        } else {

            apiInterface.getExerciseList().subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.mainThread()).subscribeWith(new DisposableObserver<ExerciseDataEntity>() {
                @Override
                public void onNext(ExerciseDataEntity exerciseList) {

                    mutableIsLoading.setValue(false);

                    if (exerciseList != null) {

                        exerciseDatabase.exerciseDao().deleteExerciseData();

                        // insert exercise data into database
                        exerciseDatabase.exerciseDao().insertExerciseList(exerciseList);

                    } else {

                        // set error here if response is null
                        mutableErrorData.setValue(application.getResources().getString(R.string.error_try_later));
                    }
                }

                @Override
                public void onError(Throwable e) {
                    //  error here since request failed
                    mutableIsLoading.setValue(false);
                    mutableErrorData.setValue(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });

        }

    }

}
