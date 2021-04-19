package com.cybarz.driverapp;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationItemView bot_view;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(savedInstanceState!=null){
            System.out.println("hellosssssssss");


        }

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.homefrag,new HomeFragment(),"new frag").commit();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ey-auto-2017a.firebaseio.com/");
        DatabaseReference mref=database.getReference();



        mref.child("driver").child("user").child(auth.getUid()).child("alerts").child("dssignal").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    View fr=(View) findViewById(R.id.cardname);
                    fr.bringToFront();
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    PreqFragment preqfragment=new PreqFragment();
                    System.out.println(snapshot.getValue());
                    ft.remove(preqfragment);


                    ft.replace(R.id.reqfrg,preqfragment,"preq fragement called").commit();

                }catch (Exception e){

                    View fr=(View) findViewById(R.id.cardname);
                    fr.bringToFront();
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    PreqFragment preqfragment=new PreqFragment();
                    System.out.println(snapshot.getValue());
                    ft.remove(preqfragment);


                    ft.replace(R.id.reqfrg,preqfragment,"preq fragement called").commitAllowingStateLoss();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mref.child("driver").child("user").child(auth.getUid()).child("Passangeravail").child("Fromloc").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        try {
                            System.out.println("chiled changed");
                            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                            PreqFragment preqfragment=new PreqFragment();
                            System.out.println(snapshot.getValue());


                            ft.replace(R.id.reqfrg,preqfragment,"preq fragement called").commit();

                        }catch (Exception e){

                            SharedPreferences settings = getApplicationContext().getSharedPreferences( "pinfo",0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("pavail", 1);

// Apply the edits!
                            editor.apply();

                            System.out.println("chiled changed");
                            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                            PreqFragment preqfragment=new PreqFragment();
                            System.out.println(snapshot.getValue());


                            ft.replace(R.id.reqfrg,preqfragment,"preq fragement called").commitAllowingStateLoss();




                        }

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



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