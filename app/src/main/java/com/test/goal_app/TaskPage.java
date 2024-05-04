package com.test.goal_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.test.goal_app.list_adapter.MainListAdapter;

import kotlinx.coroutines.scheduling.Task;

//// TaskPage displays a specific task

public class TaskPage extends AppCompatActivity {

    Button btn_taskPageBack, btn_editTask, btn_saveEdits, bt_addSubtask;
    EditText et_name, et_shortDescription, et_longDescription, et_deadLine;
    ListView lv_subTaskList;

    TaskDataBaseHelper db;

    MainListAdapter mainListAdapter;

    String backString;

    TaskModel task;

    int taskID;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        Intent intent = getIntent();
        taskID = intent.getIntExtra("taskID", 0);
        backString = intent.getStringExtra("BackString");


        btn_taskPageBack = findViewById(R.id.btn_taskPageBack);
        btn_editTask = findViewById(R.id.btn_editTask);
        et_name = findViewById(R.id.et_name);
        et_shortDescription = findViewById(R.id.et_shortDescription);
        et_longDescription = findViewById(R.id.et_longDescription);
        et_deadLine = findViewById(R.id.et_deadLine);
        btn_saveEdits = findViewById(R.id.btn_saveEdits);
        bt_addSubtask = findViewById(R.id.bt_addSubtask);
        lv_subTaskList = findViewById(R.id.lv_subTaskList);

        getSupportActionBar().hide();



        btn_saveEdits.setVisibility(View.GONE);

        db = new TaskDataBaseHelper(TaskPage.this);
        task = db.getByID(taskID);


        mainListAdapter = new MainListAdapter(this,R.layout.home_list_row,db.getAllSubtask(taskID), "TaskPage");
        mainListAdapter.parentTask = taskID;
        lv_subTaskList.setAdapter(mainListAdapter);


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
                task.setName(et_name.getText().toString());
                task.setShortDescription(et_shortDescription.getText().toString());
                task.setLongDescription(et_longDescription.getText().toString());
                task.setDeadlineDate(et_deadLine.getText().toString());

                //adds to db
                db.updateTask(task);

            }
        });

        bt_addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTask();
            }
        });



        // goes back to task list page
        btn_taskPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAction();

            }
        });
    }


    public void openAddTask(){
        Intent intent = new Intent(this, AddTask.class);
        intent.putExtra("parentTask", taskID);
        intent.putExtra("BackString",backString);
        startActivity(intent);
    }


    // function to go back to task list page
    public void backAction(){

        if(task.getParentTaskID() == -1)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragToOpen", backString);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, TaskPage.class);
            intent.putExtra("taskID", task.getParentTaskID());
            startActivity(intent);
        }


    }
}