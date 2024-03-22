package com.test.goal_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//// TaskPage displays a specific task

public class TaskPage extends AppCompatActivity {

    Button btn_taskPageBack, btn_editTask, btn_saveEdits;
    EditText et_name, et_shortDescription, et_longDescription, et_deadLine;

    TaskDataBaseHelper db;

    TaskModel task;

    int taskID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        Intent intent = getIntent();
        taskID = intent.getIntExtra("taskID", 0);


        btn_taskPageBack = findViewById(R.id.btn_taskPageBack);
        btn_editTask = findViewById(R.id.btn_editTask);
        et_name = findViewById(R.id.et_name);
        et_shortDescription = findViewById(R.id.et_shortDescription);
        et_longDescription = findViewById(R.id.et_longDescription);
        et_deadLine = findViewById(R.id.et_deadLine);
        btn_saveEdits = findViewById(R.id.btn_saveEdits);

        btn_saveEdits.setVisibility(View.GONE);

        db = new TaskDataBaseHelper(TaskPage.this);
        task = db.getByID(taskID);

        if(task != null)
        {
            et_name.setText(task.getName());
            et_shortDescription.setText(task.getShortDescription());
            et_longDescription.setText(task.getLongDescription());
            et_deadLine.setText(task.getDeadlineDate());
        }

        //desables editable texts
        et_name.setEnabled(false);
        et_shortDescription.setEnabled(false);
        et_longDescription.setEnabled(false);
        et_deadLine.setEnabled(false);


        btn_editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //enables editable texts
                et_name.setEnabled(true);
                et_shortDescription.setEnabled(true);
                et_longDescription.setEnabled(true);
                et_deadLine.setEnabled(true);

                //hides edit button shows save button
                btn_editTask.setVisibility(View.GONE);
                btn_saveEdits.setVisibility(View.VISIBLE);

            }
        });

        btn_saveEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //desables editable texts
                et_name.setEnabled(false);
                et_shortDescription.setEnabled(false);
                et_longDescription.setEnabled(false);
                et_deadLine.setEnabled(false);

                //hides save button shows edit button
                btn_saveEdits.setVisibility(View.GONE);
                btn_editTask.setVisibility(View.VISIBLE);

                //new task model
                TaskModel newTask = new TaskModel(task.getId(), et_name.getText().toString(), et_shortDescription.getText().toString(),
                        et_longDescription.getText().toString(), et_deadLine.getText().toString(), task.getCompleted(),
                        task.getCreatedDate(), task.getCompletedDate(), task.getParentTaskID());

                //adds to db
                db.updateTask(newTask);

                task = newTask;

            }
        });



        // goes back to task list page
        btn_taskPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    // function to go back to task list page
    public void openTaskList(){
        Intent intent = new Intent(this, TestDB.class);
        startActivity(intent);
    }
}