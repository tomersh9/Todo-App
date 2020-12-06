package com.example.todoroomapp.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoroomapp.database.DatabaseHelper;
import com.example.todoroomapp.model.Task;

import java.util.List;


//must extend AndroidViewModel to NOT depend on Activity/Fragment life cycle!!!
//regular ViewModel does NOT have Context!
//So.. if we need context in the ViewModel class, we create AndroidVM, NOT VM!

public class AddTaskViewModel extends AndroidViewModel {

    private DatabaseHelper databaseHelper;

    //here we create an Helper instance with the App Context
    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = new DatabaseHelper(application.getApplicationContext());
    }

    //methods we will use
    public void addTask(Task task) {
        databaseHelper.addTask(task);
    }

    public void removeTask(Task task) {
        databaseHelper.deleteTask(task);
    }

    public void addTaskList(List<Task> tasks) {
        databaseHelper.addTaskList(tasks);
    }

    public void updateTask(Task task) {
        databaseHelper.updateTask(task);
    }

}
