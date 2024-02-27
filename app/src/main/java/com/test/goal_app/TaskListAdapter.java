package com.test.goal_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


//Adapter for task list for display

public class TaskListAdapter extends ArrayAdapter<TaskModel> {

    private Context mContext;
    private int mResourse;


    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskModel> objects) {
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

        tv_rowName.setText(getItem(position).getName());
        tv_rowShortDescription.setText(getItem(position).getShortDescription());


        return convertView;
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
