package com.example.todoroomapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.todoroomapp.model.Task;

import java.util.List;

//ONLY with this helper I access the DB
public class DatabaseHelper {

    private TaskDao taskDao;

    //creating once the DB instance in the helper and an instance of the Dao
    public DatabaseHelper(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        taskDao = database.getTaskDao();
    }

    //*****implement the Tasks methods*******//

    public LiveData<List<Task>> getTasks() {
        return taskDao.getAll();
    }

    public void addTask(Task task) {
        taskDao.insert(task);
    }

    public void addTaskList(List<Task> taskList) {
        taskDao.insert(taskList);
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void updateTaskList(List<Task> taskList) {
        taskDao.update(taskList);
    }

    public void deleteTask(Task task) {
        taskDao.delete(task);
    }

}
