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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.goal_app.MainActivity;
import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.TaskModel;
import com.test.goal_app.TaskPage;

import java.util.ArrayList;



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

public class CompletedListAdapter extends ArrayAdapter<TaskModel> {

    private Context mContext;
    private int mResourse;


    public CompletedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResourse, parent, false);

        TextView tv_rowName = convertView.findViewById(R.id.tv_rowName);
        TextView tv_rowShortDescription = convertView.findViewById(R.id.tv_rowShortDescription);
        CheckBox cb_compIsCompleted = convertView.findViewById(R.id.cb_compIsCompleted);

        TaskDataBaseHelper db = new TaskDataBaseHelper(getContext());

        tv_rowName.setText(getItem(position).getName());
        tv_rowShortDescription.setText(getItem(position).getShortDescription());


        cb_compIsCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TaskModel temp = getItem(position);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(!isChecked)
                        {
                            remove(temp);
                            temp.setCompleted(false);
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

                if(!cb_isCompleted.isChecked())
                {
                    remove(temp);
                    temp.setCompleted(false);
                    db.updateTask(temp);
                }



            }
        });
        */



        return convertView;
    }

}
