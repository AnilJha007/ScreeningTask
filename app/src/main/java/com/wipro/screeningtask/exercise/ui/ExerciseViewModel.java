package com.wipro.screeningtask.exercise.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.wipro.screeningtask.database.entity.ExerciseDataEntity;


public class ExerciseViewModel extends ViewModel {

    private ExerciseRepository exerciseRepository;
    private LiveData<ExerciseDataEntity> exerciseLiveData;

    public ExerciseViewModel(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // get exercise list either from database or server
    public LiveData<ExerciseDataEntity> getExerciseList(boolean isFromPullRefresh) {

        if (isFromPullRefresh) {
            return exerciseLiveData = exerciseRepository.getExerciseList(true);
        } else {
            if (exerciseLiveData == null) {
                exerciseLiveData = exerciseRepository.getExerciseList(false);
            }
            return exerciseLiveData;
        }
    }

    // get loading state
    public LiveData<Boolean> getLoadingState() {
        return exerciseRepository.getLoadingState();
    }

    // get error message
    public MutableLiveData<String> getErrorMessage() {
        return exerciseRepository.getErrorMessage();
    }
}
