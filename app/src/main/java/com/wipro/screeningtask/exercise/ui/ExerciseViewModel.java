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
    public LiveData<List<ExerciseEntity>> getExerciseList() {
        if (exerciseLiveData == null) {
            exerciseLiveData = exerciseRepository.getExerciseList();
        }
        return exerciseLiveData;
    }

    // get loading state
    public LiveData<Boolean> getLoadingState() {
        return exerciseRepository.getLoadingState();
    }

    // get error message
    public LiveData<String> getErrorMessage() {
        return exerciseRepository.getErrorMessage();
    }

    // get updated exercise list from server using pull to refresh feature
    public LiveData<List<ExerciseEntity>> getUpdatedExerciseList() {
        return exerciseRepository.getUpdatedExerciseList();
    }
}
