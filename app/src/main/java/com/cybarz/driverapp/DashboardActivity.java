package com.cybarz.driverapp;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationItemView bot_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.homefrag,new HomeFragment(),"new frag").commit();



        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                System.out.println("Exit callback called");
                // Handle the back button event
                DashboardActivity.this.finish();

                System.exit(0);



            }
        };
       this.getOnBackPressedDispatcher().addCallback(this, callback);


    }
}