package com.wipro.screeningtask.network;

import com.wipro.screeningtask.exercise.pojo.ExerciseList;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<ExerciseList> getExerciseList();
}
