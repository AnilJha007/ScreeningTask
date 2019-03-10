package com.wipro.screeningtask.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wipro.screeningtask.exercise.pojo.ExerciseListItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExerciseConverter {

    @TypeConverter
    public String fromExerciseList(ArrayList<ExerciseListItem> listItems) {
        if (listItems == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ExerciseListItem>>() {
        }.getType();
        String json = gson.toJson(listItems, type);
        return json;
    }

    @TypeConverter
    public ArrayList<ExerciseListItem> toExerciseList(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ExerciseListItem>>() {
        }.getType();

        return gson.fromJson(itemString, type);
    }
}
