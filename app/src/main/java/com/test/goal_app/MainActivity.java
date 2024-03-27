package com.test.goal_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.test.goal_app.databinding.ActivityMainBinding;
import com.test.goal_app.ui.calendar.CalendarFragment;
import com.test.goal_app.ui.dashboard.DashboardFragment;
import com.test.goal_app.ui.home.HomeFragment;
import com.test.goal_app.ui.statistics.StatisticsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    BottomNavigationView navView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);


        Intent intent = getIntent();
        String fragToOpen = intent.getStringExtra("fragToOpen");
        if(fragToOpen != null)
        {
            if(fragToOpen.equals("Home"))
            {
                navView.setSelectedItemId(R.id.navigation_home);
            }
            else if(fragToOpen.equals("Calendar"))
            {
                navView.setSelectedItemId(R.id.navigation_calendar);
            }
            else if(fragToOpen.equals("Dashboard"))
            {
                navView.setSelectedItemId(R.id.navigation_dashboard);
            }

        }
        else {
            navView.setSelectedItemId(R.id.navigation_home);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if(itemId == R.id.navigation_calendar) {
                replaceFragment(new CalendarFragment());
                return true;
            }
            else if (itemId == R.id.navigation_dashboard) {
                replaceFragment(new DashboardFragment());
                return true;
            }
            else if (itemId == R.id.navigation_statistics) {
                replaceFragment(new StatisticsFragment());
                return true;
            }
            else if (itemId == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
                return true;
            }



        return false;
    }

    private void replaceFragment(Fragment newFragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
    }
}