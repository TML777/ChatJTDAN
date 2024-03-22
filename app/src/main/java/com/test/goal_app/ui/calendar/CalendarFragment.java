package com.test.goal_app.ui.calendar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.goal_app.R;
import com.test.goal_app.databinding.FragmentCalendarBinding;
import com.test.goal_app.databinding.FragmentDashboardBinding;
import com.test.goal_app.ui.dashboard.DashboardViewModel;

public class CalendarFragment extends Fragment {


    private FragmentCalendarBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



//        final TextView textView = binding.textCalendar;
//        calendarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}