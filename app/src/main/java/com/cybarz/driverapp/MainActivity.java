package com.cybarz.driverapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        pg=(ProgressBar) findViewById(R.id.pgbar);
        pg.setVisibility(View.INVISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ey-auto-2017a.firebaseio.com/");
        DatabaseReference mref=database.getReference();




       if(auth.getUid()==null){


           FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
           otpFragment motpfragment=new otpFragment();
           login mlogin=new login();

           ft.replace(R.id.logfrag,mlogin,"login fragement called").commit();


       }
       else {
           //auth.signOut();

           Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
           startActivity(intent);
       }
       // User user=new User("junaid","8848542918");
        //trackptype positionInfo=new trackptype(0,0,0);

        //personal info
       /* mref.child("driver").child("user").child(auth.getUid()).child("Personinfo").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             System.out.println("Succefully completed");
            }
        });

        //track info
        mref.child("driver").child("user").child(auth.getUid()).child("Trackinfo").setValue(positionInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println("Succefully completed");
            }
        });*/

        //get info


        /*mref.child("driver").child("user").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    if(task.getResult().getValue()==null){
                        System.out.println("new user");


                    }

                }
            }
        });*/




        setContentView(R.layout.activity_main);

    }
}