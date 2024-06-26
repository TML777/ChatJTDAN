package com.test.goal_app.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.list_adapter.MainListAdapter;
import com.test.goal_app.AddTask;
import com.test.goal_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ImageButton btn_openAddTaskPage;

    ListView lv_mainTasksList;

    MainListAdapter mainListAdapter;

    TaskDataBaseHelper db;


    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        db = new TaskDataBaseHelper(getContext());


        lv_mainTasksList = root.findViewById(R.id.lv_mainTasksList);
        btn_openAddTaskPage = root.findViewById(R.id.btn_openAddTaskPage);

        mainListAdapter = new MainListAdapter(getContext(),R.layout.home_list_row,db.getAllMain(), "Home");
        lv_mainTasksList.setAdapter(mainListAdapter);








//        lv_mainTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Toast", Toast.LENGTH_LONG).show();
//
//            }
//        });


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
        intent.putExtra("parentTask", -1);
        intent.putExtra("BackString","Home");
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