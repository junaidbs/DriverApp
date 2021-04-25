package com.cybarz.driverapp;

import android.Manifest;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.BlockingDeque;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ProgressBar authprogress;
    public View startebg;
    public View seccured;
    private int lock=1;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        authprogress=v.findViewById(R.id.authprogress);
        seccured=v.findViewById(R.id.seccured);


        final Button Dreiver_Verification = v.findViewById(R.id.starter);
         startebg=v.findViewById(R.id.strterbg);
        Dreiver_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast= Toast. makeText(getContext(),"start connecting",Toast. LENGTH_SHORT);

                toast. show();
                startebg.setVisibility(View.INVISIBLE);
                authprogress.setVisibility(View.VISIBLE);
                final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    System.out.println("permissionin");
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                    startebg.setVisibility(View.VISIBLE);
                    authprogress.setVisibility(View.INVISIBLE);

                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 13, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        System.out.println(location.getLatitude());
                        locationManager.removeUpdates(this);
                        if(lock==1){
                            lock=0;
                            sendCredential(location.getLatitude(),location.getLongitude());

                        }


                    }
                });
            }
        });
        return v;
    }

    public void sendCredential(Double posLat,Double Poslont){

        RequestQueue request= Volley.newRequestQueue(getContext());

        System.out.println(auth.getUid());

        String postUrl = "http://192.168.43.63:3000/api/autherise";
        JSONObject postData=new JSONObject();

        try {
            postData.put("Uid", auth.getUid());
            postData.put("Current_latitude", posLat);
            postData.put("Current_longitude", Poslont);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.getInt("authstatus")==1){
                        System.out.println("success");
                        System.out.println(response);
                        startebg.setVisibility(View.INVISIBLE);
                        authprogress.setVisibility(View.INVISIBLE);
                        seccured.setVisibility(View.VISIBLE);
                        lock=1;


                    }
                    else if(response.getInt("authstatus")==2){

                        System.out.println("failed");
                        System.out.println(response);
                        startebg.setVisibility(View.VISIBLE);
                        authprogress.setVisibility(View.INVISIBLE);
                        seccured.setVisibility(View.INVISIBLE);
                        lock=1;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    lock=1;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                startebg.setVisibility(View.VISIBLE);
                authprogress.setVisibility(View.INVISIBLE);
                seccured.setVisibility(View.INVISIBLE);
                Toast toast= Toast. makeText(getContext(),"Network error",Toast. LENGTH_SHORT);
                lock=1;

                toast. show();

                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                0,
                0));

        request.add(jsonObjectRequest);




    }

}