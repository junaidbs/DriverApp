package com.cybarz.driverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Networkchange extends BroadcastReceiver {
    boolean flag;
    boolean flag1=true;
    private Timer time;
    ScheduledFuture<?> future;
    ScheduledExecutorService scheduledExecutorService;
    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            if (isOnline(context)) {


                stopmessage();



                System.out.println("Online");
                Log.e("", "Online Connect Intenet ");
            } else {



                    sendMessagestrt();




                System.out.println("Offline");
                Log.e("", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void sendMessagestrt(){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
       scheduledExecutorService = Executors.newScheduledThreadPool(1);
       future=scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside pool ");
            }
        },0,10, TimeUnit.SECONDS);


    }

    public void stopmessage(){

        try {
            future.cancel(true);
        }catch (Exception e){

        }





    }


}
