package com.wipro.screeningtask.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wipro.screeningtask.database.dao.ExerciseDao;
import com.wipro.screeningtask.database.entity.ExerciseEntity;

@Database(entities = {ExerciseEntity.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public static synchronized ExerciseDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    ExerciseDatabase.class, "exercise_database")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract ExerciseDao exerciseDao();


}
