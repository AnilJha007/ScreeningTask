package com.wipro.screeningtask.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.wipro.screeningtask.database.dao.ExerciseDao;
import com.wipro.screeningtask.database.entity.ExerciseDataEntity;
import com.wipro.screeningtask.utils.ExerciseConverter;

@Database(entities = { ExerciseDataEntity.class}, version = 2, exportSchema = false)
@TypeConverters({ExerciseConverter.class})
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    // get instance of database
    public static synchronized ExerciseDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    ExerciseDatabase.class, "exercise_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    // using dao
    public abstract ExerciseDao exerciseDao();

}
