package com.wipro.screeningtask.exercise.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.wipro.screeningtask.exercise.pojo.Exercise;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository exerciseRepository;
    private MutableLiveData<List<Exercise>> exerciseMutableLiveData;

    public ExerciseViewModel(Application application, ExerciseRepository exerciseRepository) {
        super(application);
        this.exerciseRepository = exerciseRepository;
    }

    public LiveData<List<Exercise>> getExerciseList() {

        if (exerciseMutableLiveData == null) {
            exerciseMutableLiveData = exerciseRepository.getExerciseList();
        }
        return exerciseMutableLiveData;
    }

    public LiveData<Boolean> getLoadingState() {

        return exerciseRepository.getLoadingState();
    }

    public LiveData<String> getErrorMessage() {

        return exerciseRepository.getErrorMessage();
    }

    public LiveData<List<Exercise>> getUpdatedExerciseList() {

        return exerciseRepository.getUpdatedExerciseList();
    }
}
