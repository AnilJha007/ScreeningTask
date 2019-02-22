package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.database.ExerciseDatabase;
import com.wipro.screeningtask.database.entity.ExerciseEntity;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.exercise.pojo.ExerciseList;
import com.wipro.screeningtask.utils.InternetUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRepository {

    private Application application;
    private InternetUtil internetUtil;
    private MutableLiveData<Boolean> mutableIsLoading;
    private MutableLiveData<String> mutableErrorData;
    private MutableLiveData<String> mutableTitleData;

    private ExerciseDatabase exerciseDatabase;
    private ApiInterface apiInterface;

    public ExerciseRepository(Application application, InternetUtil internetUtil, ExerciseDatabase exerciseDatabase, ApiInterface apiInterface) {
        this.internetUtil = internetUtil;
        this.application = application;
        this.exerciseDatabase = exerciseDatabase;
        this.apiInterface = apiInterface;

    }

    // get loading state
    public MutableLiveData<Boolean> getLoadingState() {
        return mutableIsLoading;
    }

    // get error state
    public MutableLiveData<String> getErrorMessage() {
        return mutableErrorData;
    }

    // get title data
    public MutableLiveData<String> getTitle() {
        return mutableTitleData;
    }

    // get exercise list
    public LiveData<List<ExerciseEntity>> getExerciseList(boolean isFromPullRefresh) {

        mutableErrorData = new MutableLiveData<>();
        mutableIsLoading = new MutableLiveData<>();
        mutableTitleData= new MutableLiveData<>();

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

            Call<ExerciseList> call = apiInterface.getExerciseList();
            call.enqueue(new Callback<ExerciseList>() {
                @Override
                public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {

                    mutableIsLoading.setValue(false);

                    if (response.body() != null) {

                        ExerciseList exerciseList = response.body();

                        if (exerciseDatabase.exerciseDao().getExerciseCount() > 0)
                            exerciseDatabase.exerciseDao().deleteExerciseData();

                        mutableTitleData.setValue(response.body().getTitle());

                        // insert exercise data into database
                        exerciseDatabase.exerciseDao().insertExerciseList(exerciseList.getRows());

                    } else {

                        // set error here if response body is null
                        mutableErrorData.setValue(application.getResources().getString(R.string.error_try_later));
                    }
                }

                @Override
                public void onFailure(Call<ExerciseList> call, Throwable t) {

                    //  error here since request failed
                    mutableIsLoading.setValue(false);
                    mutableErrorData.setValue(application.getResources().getString(R.string.error_try_later));
                }
            });
        }

    }

}
