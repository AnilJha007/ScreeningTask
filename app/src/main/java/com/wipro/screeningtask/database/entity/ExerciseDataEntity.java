package com.wipro.screeningtask.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wipro.screeningtask.exercise.pojo.ExerciseListItem;
import com.wipro.screeningtask.utils.ExerciseConverter;

import java.util.ArrayList;

@Entity(tableName = "exercise_table")
public class ExerciseDataEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @TypeConverters(ExerciseConverter.class)
    @ColumnInfo(name = "rows")
    private ArrayList<ExerciseListItem> rows;

    public ExerciseDataEntity(String title, ArrayList<ExerciseListItem> rows) {
        this.title = title;
        this.rows = rows;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ExerciseListItem> getRows() {

        if (rows == null) {
            rows = new ArrayList<>();
        }
        return rows;
    }

    public void setRows(ArrayList<ExerciseListItem> rows) {
        this.rows = rows;
    }
}
