package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.wipro.screeningtask.database.entity.ExerciseEntity;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository exerciseRepository;
    private LiveData<List<ExerciseEntity>> exerciseLiveData;

    public ExerciseViewModel(Application application, ExerciseRepository exerciseRepository) {
        super(application);
        this.exerciseRepository = exerciseRepository;
    }

    // get exercise list either from database or server
    public LiveData<List<ExerciseEntity>> getExerciseList(boolean isFromPullRefresh) {

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
    public LiveData<String> getErrorMessage() {
        return exerciseRepository.getErrorMessage();
    }

}
