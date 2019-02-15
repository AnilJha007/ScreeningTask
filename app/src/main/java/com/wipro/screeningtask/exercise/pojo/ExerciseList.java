package com.wipro.screeningtask.exercise.pojo;

import com.google.gson.annotations.SerializedName;
import com.wipro.screeningtask.database.entity.ExerciseEntity;

import java.util.ArrayList;

public class ExerciseList {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private ArrayList<ExerciseEntity> rows;

    public String getTitle() {
        return title;
    }

    public ArrayList<ExerciseEntity> getRows() {
        return rows;
    }
}
