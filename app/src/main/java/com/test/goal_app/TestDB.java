package com.test.goal_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TestDB extends AppCompatActivity {

    Button btn_openAddTaskPage;

    ListView lv_mainTasksList;

    TaskListAdapter taskListAdapter;

    TaskDataBaseHelper db;

//    ArrayAdapter taskArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);


        //btn_openAddTaskPage = findViewById(R.id.btn_openAddTaskPage);
        lv_mainTasksList = findViewById(R.id.lv_mainTasksList);

        db = new TaskDataBaseHelper(TestDB.this);

//        taskArrayAdapter = new ArrayAdapter<TaskModel>(TestDB.this, android.R.layout.simple_list_item_1, db.getEveryone());
//        lv_mainTasksList.setAdapter(taskArrayAdapter);

        taskListAdapter = new TaskListAdapter(this,R.layout.task_list_row ,db.getEveryone());
        lv_mainTasksList.setAdapter(taskListAdapter);


        lv_mainTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // opens task page with clicked task
                TaskModel clickedTask = (TaskModel) parent.getItemAtPosition(position);
                openTaskPage(clickedTask);
            }
        });


        // opens add task page
        btn_openAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTaskPage();
            }
        });

    }

    //add task page funcion
    private void openAddTaskPage()
    {
        Intent intent = new Intent(this, TestDBAddTask.class);
        startActivity(intent);
    }

    //opens task page with selected task
    // selected task ID is passed as an extra named: taskID
    private void openTaskPage(TaskModel taskModel)
    {
        Intent intent = new Intent(TestDB.this, TaskPage.class);
        intent.putExtra("taskID", taskModel.getId());
        startActivity(intent);
    }


}