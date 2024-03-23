package com.test.goal_app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.list_adapter.HomeListAdapter;
import com.test.goal_app.AddTask;
import com.test.goal_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    Button btn_openAddTaskPage;

    ListView lv_mainTasksList;

    HomeListAdapter homeListAdapter;

    TaskDataBaseHelper db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        lv_mainTasksList = root.findViewById(R.id.lv_mainTasksList);
        btn_openAddTaskPage = root.findViewById(R.id.btn_openAddTaskPage);

        db = new TaskDataBaseHelper(getContext());

        homeListAdapter = new HomeListAdapter(getContext(),R.layout.task_list_row ,db.getAllMain());
        lv_mainTasksList.setAdapter(homeListAdapter);








        lv_mainTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Toast", Toast.LENGTH_LONG).show();

            }
        });


        btn_openAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTaskPage();
            }
        });



        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    //add task page funcion
    private void openAddTaskPage()
    {
        Intent intent = new Intent(getActivity().getApplication(), AddTask.class);
        startActivity(intent);
    }
/*

    //opens task page with selected task
    // selected task ID is passed as an extra named: taskID
    private void openTaskPage(TaskModel taskModel)
    {
        Intent intent = new Intent(getActivity().getApplication(), TaskPage.class);
        intent.putExtra("taskID", taskModel.getId());
        startActivity(intent);
    }
*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}