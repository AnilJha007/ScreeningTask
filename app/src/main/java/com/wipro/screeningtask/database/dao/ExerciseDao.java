package com.wipro.screeningtask.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import com.wipro.screeningtask.database.entity.ExerciseEntity;

import java.util.List;

@Dao
public interface ExerciseDao {

    // insert data into database and handle conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseList(List<ExerciseEntity> exerciseList);

    // get all the data present in exercise table
    @Query("select * from exercise_table order by id asc")
    LiveData<List<ExerciseEntity>> getExercise();

    // clear the table
    @Query("delete from exercise_table")
    void deleteExerciseData();

    // get the row count from the table
    @Query("select count(*) from exercise_table")
    long getExerciseCount();
}
