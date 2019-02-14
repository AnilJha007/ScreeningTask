package com.wipro.screeningtask.exercise.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExerciseList {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private ArrayList<Exercise> rows;

    public String getTitle() {
        return title;
    }

    public ArrayList<Exercise> getRows() {
        return rows;
    }
}
