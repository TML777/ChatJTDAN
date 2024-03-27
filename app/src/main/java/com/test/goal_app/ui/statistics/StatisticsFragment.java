package com.test.goal_app.ui.statistics;

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
import com.test.goal_app.databinding.FragmentNotificationsBinding;
import com.test.goal_app.databinding.FragmentStatisticsBinding;
import com.test.goal_app.ui.notifications.NotificationsViewModel;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatisticsViewModel statisticsViewModel =
                new ViewModelProvider(this).get(StatisticsViewModel.class);

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        root = binding.getRoot();



//        final TextView textView = binding.textStatistics;
//        statisticsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}