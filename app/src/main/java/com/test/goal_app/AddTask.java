package com.test.goal_app;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {

    ImageButton btn_addTaskBack;
    Button btn_addTaskButton;
    EditText et_addTaskName, et_addShortDesc, et_addLongDesc;
    CalendarView cv_addCalendar;

    TaskDataBaseHelper db;

    SimpleDateFormat df2;
    TaskModel taskModel;
    long deadLineDateMilli;

    int parentTask;
    String backString;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btn_addTaskBack = findViewById(R.id.btn_addTaskBack);
        btn_addTaskButton = findViewById(R.id.btn_addTaskButton);
        et_addTaskName = findViewById(R.id.et_addTaskName);
        et_addShortDesc = findViewById(R.id.et_addShortDesc);
        et_addLongDesc = findViewById(R.id.et_addLongDesc);
        cv_addCalendar = findViewById(R.id.cv_addCalendar);

        getSupportActionBar().hide();

        db = new TaskDataBaseHelper(AddTask.this);



        Intent intent = getIntent();
        parentTask = intent.getIntExtra("parentTask", -1);
        backString = intent.getStringExtra("BackString");


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


                String name = et_addTaskName.getText().toString();
                if(name.isEmpty()) {
                    et_addTaskName.setBackground(getDrawable(R.drawable.red_border));

                    Toast.makeText( AddTask.this, "Please name the task!", Toast.LENGTH_SHORT).show();

                } else{
                    et_addTaskName.setBackground(getDrawable(R.drawable.green_border));


                    try{
                        String formatedDate = df2.format(new Date(deadLineDateMilli));

                        taskModel = new TaskModel(-1, et_addTaskName.getText().toString(), et_addShortDesc.getText().toString(),
                                et_addLongDesc.getText().toString(), formatedDate,
                                false, false, df2.format(new Date()), "none", parentTask);
                        //Toast.makeText( AddTask.this, taskModel.toString() + " " + df2.format(new Date()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText( AddTask.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                        taskModel = new TaskModel(-1, "error", "error", "error",
                                "error", false, false,  "error", "error",parentTask);

                    }
                    TaskDataBaseHelper dataBaseHelper = new TaskDataBaseHelper(AddTask.this);

                    boolean success = dataBaseHelper.addOne(taskModel);

                    if(success)
                        Toast.makeText( AddTask.this, "Task Created!" + success, Toast.LENGTH_SHORT).show();

                    // back to task list
                    openTaskList();
                }

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
        if(parentTask == -1)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragToOpen", backString);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, TaskPage.class);
            intent.putExtra("taskID", parentTask);
            intent.putExtra("BackString",backString);
            startActivity(intent);
        }
    }




}