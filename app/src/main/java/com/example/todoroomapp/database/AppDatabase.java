package com.example.todoroomapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todoroomapp.model.Task;

@Database(entities = Task.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "database.db";
    private static AppDatabase database;

    //no need for empty c'tor

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,AppDatabase.class,DB_NAME)
                    .allowMainThreadQueries().build(); //we can allow because LiveData already works on back thread
        }
        return database;
    }

    public abstract TaskDao getTaskDao(); //for the helper class
}
