package com.test.goal_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TestDBAddTask extends AppCompatActivity {

    Button btn_addTaskBack, btn_addTaskButton;
    EditText et_addTaskName, et_addShortDesc, et_addLongDesc;
    CalendarView cv_addCalendar;

    TaskDataBaseHelper db;

    SimpleDateFormat df2;
    TaskModel taskModel;
    long deadLineDateMilli;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db_add_task);

        btn_addTaskBack = findViewById(R.id.btn_addTaskBack);
        btn_addTaskButton = findViewById(R.id.btn_addTaskButton);
        et_addTaskName = findViewById(R.id.et_addTaskName);
        et_addShortDesc = findViewById(R.id.et_addShortDesc);
        et_addLongDesc = findViewById(R.id.et_addLongDesc);
        cv_addCalendar = findViewById(R.id.cv_addCalendar);

        db = new TaskDataBaseHelper(TestDBAddTask.this);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDateTime now = LocalDateTime.now();



////////////////// Sets Min date to today
        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        try {
            String string_date = f.format(date);
            Date d = f.parse(string_date);
            long milliseconds = d.getTime();
            cv_addCalendar.setMinDate(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


///////// Calendar change listener
        cv_addCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
//                Toast.makeText(getApplicationContext(), month + "/" + day + "/" + year, Toast.LENGTH_LONG).show();
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);

                // new clicked date in long, used for adding a task
                deadLineDateMilli = c.getTimeInMillis();
            }
        });

///////// Adding a task button
        btn_addTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                df2 = new SimpleDateFormat("MM/dd/yy");


                try{
                    String formatedDate = df2.format(new Date(deadLineDateMilli));

                    taskModel = new TaskModel(-1, et_addTaskName.getText().toString(), et_addShortDesc.getText().toString(),
                            et_addLongDesc.getText().toString(), formatedDate,
                            false, df2.format(new Date()), "none", -1);
                    Toast.makeText( TestDBAddTask.this, taskModel.toString() + " " + df2.format(new Date()), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText( TestDBAddTask.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    taskModel = new TaskModel(-1, "error", "error", "error",
                            "error", false,  "error", "error",-1);

                }
                TaskDataBaseHelper dataBaseHelper = new TaskDataBaseHelper(TestDBAddTask.this);

                boolean success = dataBaseHelper.addOne(taskModel);

                Toast.makeText( TestDBAddTask.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                // back to task list
                openTaskList();
            }
        });


        // back to task list
        btn_addTaskBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // back to task list function
    public void openTaskList(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}