package com.test.goal_app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.databinding.FragmentDashboardBinding;
import com.test.goal_app.list_adapter.CompletedListAdapter;
import com.test.goal_app.list_adapter.DeletedListAdapter;
import com.test.goal_app.list_adapter.HomeListAdapter;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ListView lv_completedList, lv_deletedList;
    CompletedListAdapter completedListAdapter;
    DeletedListAdapter deletedListAdapter;
    TaskDataBaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new TaskDataBaseHelper(getContext());


        lv_completedList = root.findViewById(R.id.lv_completedList);
        completedListAdapter = new CompletedListAdapter(getContext(),R.layout.completed_list_row,db.getAllCompleted());
        lv_completedList.setAdapter(completedListAdapter);


        lv_deletedList = root.findViewById(R.id.lv_deletedList);
        deletedListAdapter = new DeletedListAdapter(getContext(),R.layout.deleted_list_row,db.getAllDeleted());
        lv_deletedList.setAdapter(deletedListAdapter);


//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void reloadDatabase(){
        completedListAdapter = new CompletedListAdapter(getContext(),R.layout.completed_list_row,db.getAllCompleted());
        lv_completedList.setAdapter(completedListAdapter);
        deletedListAdapter = new DeletedListAdapter(getContext(),R.layout.deleted_list_row,db.getAllDeleted());
        lv_deletedList.setAdapter(deletedListAdapter);

    }
}