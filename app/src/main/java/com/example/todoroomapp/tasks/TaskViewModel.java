package com.example.todoroomapp.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoroomapp.database.DatabaseHelper;
import com.example.todoroomapp.model.Task;

import java.util.List;


//must extend AndroidViewModel to NOT depend on Activity/Fragment life cycle!!!
//regular ViewModel does NOT have Context!
//So.. if we need context in the ViewModel class, we create AndroidVM, NOT VM!

public class TaskViewModel extends AndroidViewModel {

    private LiveData<List<Task>> tasks;
    private DatabaseHelper databaseHelper;

    //here we create an Helper instance with the App Context
    public TaskViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = new DatabaseHelper(application.getApplicationContext());
    }

    //The main function we will Observe outside is this
    LiveData<List<Task>> getTasks() {
        if (tasks == null) {
            tasks = databaseHelper.getTasks();
        }
        return tasks;
    }

    void deleteTask(Task task) {
        databaseHelper.deleteTask(task);
    }

    void updateTask(Task task) {
        databaseHelper.updateTask(task);
    }
}
