package com.wipro.screeningtask.utils;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.wipro.screeningtask.exercise.ui.ExerciseRepository;
import com.wipro.screeningtask.exercise.ui.ExerciseViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private ExerciseRepository exerciseRepository;

    public ViewModelFactory(Application application, ExerciseRepository exerciseRepository) {
        this.application = application;
        this.exerciseRepository = exerciseRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (ExerciseViewModel.class.isAssignableFrom(modelClass)) {
            return (T) new ExerciseViewModel(application, exerciseRepository);

        }

        return null;
    }
}
