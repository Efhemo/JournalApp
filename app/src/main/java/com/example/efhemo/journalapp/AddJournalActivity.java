package com.example.efhemo.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.efhemo.journalapp.database.AppDatabase;
import com.example.efhemo.journalapp.database.AppExecutors;
import com.example.efhemo.journalapp.model.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddJournalActivity extends AppCompatActivity implements
        View.OnClickListener {




    AppDatabase mDb;

    EditText editTextTitle;
    EditText editTextDescription;
    Button buttonSave;
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_write);
        buttonSave = findViewById(R.id.save_button);
        buttonSave.setOnClickListener(this);





                    //TODO
        Intent intent = getIntent();


    }



    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        editTextTitle.setText(task.getJournalTitle());
        editTextDescription.setText(task.getJournalDescription());
        //setPriorityInViews(task.getPriority());
    }


    /*onClick for save button*/
    @Override
    public void onClick(View view) {
        String myTitle = editTextTitle.getText().toString();
        String myWrite = editTextDescription.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        /*Convert Date datatype to String*/
        String formattedDate = df.format(c);

        /*Check if each Entry is null*/
        if (myTitle.isEmpty() || myWrite.isEmpty()) {
            //Todo: put Stack bar instead of toast
            Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT).show();
            //Snackbar.make(this,  "All field must be filled", Snackbar.LENGTH_SHORT).show();
            return;
        }

        TaskEntry taskEntry = new TaskEntry(myTitle, myWrite, formattedDate); //Todo: i can come back to change it to Data and time

        extractJsonAnotherThread(taskEntry);

        finish();
    }

    /*writing to database is done in worker thread*/
    void extractJsonAnotherThread(final TaskEntry taskEntry) {
        AppExecutors.getsInstance().getDiskIO()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb = AppDatabase.getsInstance(getApplicationContext());
                        mDb.taskDao().insertJournalTask(taskEntry);
                    }
                });
    }
}
