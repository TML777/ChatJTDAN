package com.test.goal_app.list_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class HomeListAdapter extends ArrayAdapter<TaskModel> {

    private Context mContext;
    private int mResourse;


    public HomeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResourse,parent,false);

        TextView tv_rowName = convertView.findViewById(R.id.tv_rowName);
        TextView tv_rowShortDescription = convertView.findViewById(R.id.tv_rowShortDescription);
        Button btn_listButton = convertView.findViewById(R.id.btn_listDelete);
        Button btn_taskPage = convertView.findViewById(R.id.btn_taskPage);
        CheckBox cb_isCompleted = convertView.findViewById(R.id.cb_isCompleted);

        TaskDataBaseHelper db = new TaskDataBaseHelper(getContext());

        tv_rowName.setText(getItem(position).getName());
        tv_rowShortDescription.setText(getItem(position).getShortDescription());

        btn_listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskModel temp = getItem(position);
                temp.setDeleted(true);
                db.updateTask(temp);
                Intent intent = new Intent(mContext, MainActivity.class);
                //Testing opening specific fragments
//                intent.putExtra("fragToOpen", "Calendar");
                mContext.startActivity(intent);
            }
        });

        cb_isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TaskModel temp = getItem(position);
                    temp.setCompleted(cb_isCompleted.isChecked());
                    db.updateTask(temp);
            }
        });

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
