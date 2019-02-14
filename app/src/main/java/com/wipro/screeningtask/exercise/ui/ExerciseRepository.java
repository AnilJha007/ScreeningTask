package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wipro.screeningtask.network.ApiClient;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.exercise.pojo.Exercise;
import com.wipro.screeningtask.exercise.pojo.ExerciseList;
import com.wipro.screeningtask.utils.ConstantUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRepository {

    private static final String TAG = ExerciseRepository.class.getSimpleName();
    private SharedPreferences.Editor editor;

    public ExerciseRepository(Application application) {
        editor = application.getSharedPreferences(ConstantUtil.SHARED_PREF, Context.MODE_PRIVATE).edit();
    }

    public MutableLiveData<List<Exercise>> getExerciseList() {

        final MutableLiveData<List<Exercise>> mutableExerciseList = new MutableLiveData<>();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ExerciseList> call = apiService.getExerciseList();
        call.enqueue(new Callback<ExerciseList>() {
            @Override
            public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {

                if (response.body() != null) {
                    ExerciseList exerciseList = response.body();
                    editor.putString(ConstantUtil.TOOLBAR_TITLE, response.body().getTitle()).apply();
                    mutableExerciseList.setValue(exerciseList.getRows());
                }
            }

            @Override
            public void onFailure(Call<ExerciseList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


        return mutableExerciseList;
    }
}
