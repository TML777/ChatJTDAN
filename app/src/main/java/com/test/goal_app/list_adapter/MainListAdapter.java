package com.test.goal_app.list_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.goal_app.MainActivity;
import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.TaskModel;
import com.test.goal_app.TaskPage;

import java.util.ArrayList;


//Adapter for task list for display

public class MainListAdapter extends ArrayAdapter<TaskModel> {

    private Context mContext;
    private int mResourse;
    private String backString;
    public int parentTask;

    TextView tv_rowName;
    TextView tv_rowShortDescription;
    ImageButton btn_listDelete;
    ImageButton btn_taskPage;
    CheckBox cb_isCompleted;




    public MainListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourse = resource;
        backString = "Home";
        parentTask = -1;
    }

    public MainListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskModel> objects, String backString) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourse = resource;
        this.backString = backString;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResourse,parent,false);

        tv_rowName = convertView.findViewById(R.id.tv_rowName);
        tv_rowShortDescription = convertView.findViewById(R.id.tv_rowShortDescription);
        btn_listDelete = convertView.findViewById(R.id.btn_listDelete);
        btn_taskPage = convertView.findViewById(R.id.btn_taskPage);
        cb_isCompleted = convertView.findViewById(R.id.cb_isCompleted);

        TaskDataBaseHelper db = new TaskDataBaseHelper(getContext());

        tv_rowName.setText(getItem(position).getName());
        tv_rowShortDescription.setText(getItem(position).getShortDescription());

        btn_listDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskModel temp = getItem(position);
                remove(temp);
                temp.setDeleted(true);
                db.updateTask(temp);

                //Testing opening specific fragments
//                if(!backString.equals(null)){
//                    if(backString.equals("TaskPage"))
//                    {
//                        Intent intent = new Intent(mContext, TaskPage.class);
//                        intent.putExtra("taskID", parentTask);
//                        mContext.startActivity(intent);
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(mContext, MainActivity.class);
//                        intent.putExtra("fragToOpen", backString);
//                        mContext.startActivity(intent);
//                    }
//                }

            }
        });

        cb_isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TaskModel temp = getItem(position);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(isChecked)
                        {
                            remove(temp);
                            temp.setCompleted(true);
                            db.updateTask(temp);
                        }
                    }
                }, 500);



            }
        });
/*

        cb_isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TaskModel temp = getItem(position);
                    if(cb_isCompleted.isChecked())
                    {
                        remove(temp);
                       temp.setCompleted(true);
                       db.updateTask(temp);
                    }
            }
        });
*/

        btn_taskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskPage(getItem(position));
            }
        });





        return convertView;
    }

    //opens task page with selected task
    // selected task ID is passed as an extra named: taskID
    private void openTaskPage(TaskModel taskModel)
    {
        Intent intent = new Intent(mContext, TaskPage.class);
        intent.putExtra("taskID", taskModel.getId());
        intent.putExtra("BackString",backString);
        mContext.startActivity(intent);
    }


    /*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TaskModel task = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_list_view,parent,false);
        }


        TextView tv_listViewTaskName = convertView.findViewById(R.id.tv_listViewTaskName);
        TextView tv_listViewShortDescription = convertView.findViewById(R.id.tv_listViewShortDescription);

        tv_listViewTaskName.setText(task.getName());
        tv_listViewShortDescription.setText(task.getShortDescription());



        return super.getView(position, convertView, parent);
    }
    */


}
