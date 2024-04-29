package com.test.goal_app.ui.calendar;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.goal_app.AddTask;
import com.test.goal_app.R;
import com.test.goal_app.TaskDataBaseHelper;
import com.test.goal_app.TaskModel;
import com.test.goal_app.databinding.FragmentCalendarBinding;
import com.test.goal_app.list_adapter.MainListAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener{


    private FragmentCalendarBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private View root;
    private Button btn_nextMonth;
    private Button btn_prevMonth;
    private ListView lv_selectedDateList;
    private MainListAdapter mainListAdapter;

    private TaskDataBaseHelper db;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        btn_nextMonth = root.findViewById(R.id.btn_nextMonth);
        btn_prevMonth = root.findViewById(R.id.btn_prevMonth);
        lv_selectedDateList = root.findViewById(R.id.lv_selectedDateList);

        db = new TaskDataBaseHelper(getContext());

        btn_nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction();
            }
        });
        btn_prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction();
            }
        });




        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
//        final TextView textView = binding.textCalendar;
//        calendarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initWidgets()
    {
        calendarRecyclerView = root.findViewById(R.id.rv_calendar);
        monthYearText = root.findViewById(R.id.tv_monthYear);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        ArrayList<Boolean> hasTask = hasTaskArray(selectedDate, daysInMonth);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, hasTask,this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    private ArrayList<Boolean> hasTaskArray(LocalDate date, ArrayList<String> daysInMonth)
    {
//        ArrayList<Boolean> hasTask = new ArrayList<>();
//        boolean[] taskDate = new boolean[31];
//        TaskModel tempTask;
//        String deadLineDate;
//        String tempDayInMonth;
//        int day;
//
//        SimpleDateFormat df1 = new SimpleDateFormat("MM");
//        String formatedMonth = df1.format(date);
//
//        SimpleDateFormat df2 = new SimpleDateFormat("YY");
//        String formatedYear = df2.format(date);
//
//        ArrayList<TaskModel> taskList = db.getAllMain();
//
//        Iterator it = taskList.iterator();
//
//        while(it.hasNext())
//        {
//            tempTask = (TaskModel) it.next();
//            deadLineDate = tempTask.getDeadlineDate();
//
//            String[] parts = deadLineDate.split("/");
//
//            day = Integer.parseInt(parts[1]);
//
//            taskDate[day] = true;
//
//        }
//
//        it = daysInMonth.iterator();
//
//        while(it.hasNext()){
//            tempDayInMonth = (String) it.next();
//
//            if(!tempDayInMonth.equals(""))
//            {
//                day = Integer.parseInt(tempDayInMonth);
//                if(taskDate[day])
//                    hasTask.add(true);
//                else
//                    hasTask.add(false);
//            }
//            else
//                hasTask.add(false);
//        }
//

        boolean[] taskDate = new boolean[date.lengthOfMonth()]; // Adjust array size based on the month
        TaskModel tempTask;
        String deadLineDate;

        SimpleDateFormat df1 = new SimpleDateFormat("MM");
        String formatedMonth = df1.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        SimpleDateFormat df2 = new SimpleDateFormat("yy"); // Change 'YY' to 'yy'
        String formatedYear = df2.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        ArrayList<TaskModel> taskList = db.getMainByMonth(formatedMonth + "/%/" + formatedYear);

        for (TaskModel task : taskList) { // Using enhanced for-loop for better readability
            deadLineDate = task.getDeadlineDate();
            String[] parts = deadLineDate.split("/");

            if (parts.length > 1) {
                int day = Integer.parseInt(parts[1]) - 1; // Array is 0-indexed
                if (day >= 0 && day < taskDate.length) {
                    taskDate[day] = true;
                }
            }
        }

        ArrayList<Boolean> hasTask = new ArrayList<>();

        Iterator it = daysInMonth.iterator();

        for (String tempDayInMonth : daysInMonth) {
            if (!tempDayInMonth.equals("")) {
                int day = Integer.parseInt(tempDayInMonth) - 1;
                if (day >= 0 && day < taskDate.length && taskDate[day]) {
                    hasTask.add(true);
                } else {
                    hasTask.add(false);
                }
            } else {
                hasTask.add(false);
            }
        }

        //Toast.makeText( getContext(), "formatedDate", Toast.LENGTH_SHORT).show();


        return hasTask;
    }



    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction()
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
//            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
//            Toast.makeText(root.getContext(), message, Toast.LENGTH_LONG).show();

            SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yy");
            String selDate = df2.format(new Date(dayText + " " + monthYearFromDate(selectedDate)));

            mainListAdapter = new MainListAdapter(getContext(),R.layout.home_list_row,db.getAllMainOnDate(selDate), "Calendar");
            lv_selectedDateList.setAdapter(mainListAdapter);

            if(mainListAdapter.isEmpty())
            {
                Toast.makeText(root.getContext(), "empty on " + selDate, Toast.LENGTH_SHORT).show();
            }
        }
    }


}