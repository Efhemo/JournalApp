package com.example.efhemo.journalapp.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.efhemo.journalapp.database.AppDatabase;
import com.example.efhemo.journalapp.model.TaskEntry;


public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
