package com.wipro.screeningtask.network;

import com.wipro.screeningtask.exercise.pojo.ExerciseList;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<ExerciseList> getExerciseList();
}
