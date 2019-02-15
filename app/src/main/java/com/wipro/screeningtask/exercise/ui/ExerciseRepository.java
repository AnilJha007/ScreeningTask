package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.wipro.screeningtask.R;
import com.wipro.screeningtask.database.ExerciseDatabase;
import com.wipro.screeningtask.database.entity.ExerciseEntity;
import com.wipro.screeningtask.network.ApiClient;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.exercise.pojo.ExerciseList;
import com.wipro.screeningtask.utils.ConstantUtil;
import com.wipro.screeningtask.utils.InternetUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRepository {

    private SharedPreferences.Editor editor;
    private Application application;
    private InternetUtil internetUtil;
    private MutableLiveData<Boolean> mutableIsLoading;
    private MutableLiveData<String> mutableErrorData;
    private ExerciseDatabase exerciseDatabase;

    public ExerciseRepository(Application application, SharedPreferences.Editor editor, InternetUtil internetUtil, ExerciseDatabase exerciseDatabase) {
        this.editor = editor;
        this.internetUtil = internetUtil;
        this.application = application;
        this.exerciseDatabase = exerciseDatabase;

        mutableIsLoading = new MutableLiveData<>();
        mutableErrorData = new MutableLiveData<>();

    }

    public MutableLiveData<Boolean> getLoadingState() {
        return mutableIsLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        return mutableErrorData;
    }

    public LiveData<List<ExerciseEntity>> getExerciseList() {

        long rowCount = exerciseDatabase.exerciseDao().getExerciseCount();

        // call api to pull data from server because database has no data
        if (rowCount == 0) {

            mutableIsLoading.setValue(true);

            if (!internetUtil.isNetworkAvailable()) {
                mutableIsLoading.setValue(false);
                mutableErrorData.setValue(application.getResources().getString(R.string.internet_not_available));
            } else {

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<ExerciseList> call = apiService.getExerciseList();
                call.enqueue(new Callback<ExerciseList>() {
                    @Override
                    public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {

                        mutableIsLoading.setValue(false);

                        if (response.body() != null) {
                            ExerciseList exerciseList = response.body();
                            editor.putString(ConstantUtil.TOOLBAR_TITLE, response.body().getTitle()).apply();

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

        return exerciseDatabase.exerciseDao().getExercise();

    }

    public MutableLiveData<List<ExerciseEntity>> getUpdatedExerciseList() {

        final MutableLiveData<List<ExerciseEntity>> mutableExerciseList = new MutableLiveData<>();

        if (!internetUtil.isNetworkAvailable()) {
            mutableErrorData.setValue(application.getResources().getString(R.string.internet_not_available));
        } else {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ExerciseList> call = apiService.getExerciseList();
            call.enqueue(new Callback<ExerciseList>() {
                @Override
                public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {

                    if (response.body() != null) {
                        ExerciseList exerciseList = response.body();
                        editor.putString(ConstantUtil.TOOLBAR_TITLE, response.body().getTitle()).apply();

                        // deleting the stored data and inserting updated data into database
                        exerciseDatabase.exerciseDao().deleteExerciseData();
                        exerciseDatabase.exerciseDao().insertExerciseList(exerciseList.getRows());

                        mutableExerciseList.setValue(exerciseList.getRows());

                    } else {

                        // error here if response body is null
                        mutableErrorData.setValue(application.getResources().getString(R.string.error_try_later));
                    }
                }

                @Override
                public void onFailure(Call<ExerciseList> call, Throwable t) {

                    //  error here since request failed
                    mutableErrorData.setValue(application.getResources().getString(R.string.error_try_later));
                }
            });

        }

        return mutableExerciseList;
    }
}
