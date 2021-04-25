package com.cybarz.driverapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xml.sax.helpers.XMLReaderFactory;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreqFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Button accept;
    public Button decline;
    public FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference mref;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreqFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreqFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreqFragment newInstance(String param1, String param2) {
        PreqFragment fragment = new PreqFragment();
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

        database = FirebaseDatabase.getInstance("https://ey-auto-2017a.firebaseio.com/");
         mref=database.getReference();
         firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_preq, container, false);

        accept=v.findViewById(R.id.accept);
        decline=v.findViewById(R.id.decline);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setVisibility(View.INVISIBLE);
                decline.setVisibility(View.INVISIBLE);

                                                                                                                //wait for ack from passenger

                Random val =new Random();
                mref.child("driver").child("user").child(firebaseAuth.getUid()).child("Passangeravail").child("Isavailable").child("available").setValue(val.nextInt(100)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FragmentTransaction trs=getFragmentManager().beginTransaction();
                        //trs.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
                        Fragment fr=getFragmentManager().findFragmentById(R.id.reqfrg);

                        final DatabaseReference ref= mref.child("driver").child("user").child(firebaseAuth.getUid()).child("Passangeravail");

                        mref.child("driver").child("user").child(firebaseAuth.getUid()).child("Passangeravail").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                System.out.println(snapshot.child("Fromloc").child("platitude").getValue());
                                System.out.println(snapshot.child("Fromloc").child("plongitude").getValue());
                                System.out.println(snapshot.child("Toloc").child("platitude").getValue());
                                System.out.println(snapshot.child("Toloc").child("plongitude").getValue());
                                double fromlocla= (double) snapshot.child("Fromloc").child("platitude").getValue();
                                double fromloclo= (double) snapshot.child("Fromloc").child("plongitude").getValue();
                                double tolocla= (double) snapshot.child("Toloc").child("plongitude").getValue();
                                double toloclo= (double) snapshot.child("Toloc").child("plongitude").getValue();
                                System.out.println(fromlocla);
                                ref.removeEventListener(this);
                                Intent intent=new Intent( getContext(), navigationdriverActivity.class);
                                intent.putExtra("flant",fromlocla);
                                intent.putExtra("flont",fromloclo);
                                intent.putExtra("tlant",tolocla);
                                intent.putExtra("tlont",tolocla);
                                startActivity(intent);



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                        //trs.remove(fr).commit();

                    }
                });
                //accepted further development for realtime tracking



            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft= getFragmentManager().beginTransaction();
                PreqFragment preqfragment=new PreqFragment();
                System.out.println("declined successfully");
                ft.remove(preqfragment);
                View v=getActivity().findViewById(R.id.homefrag);
                v.setVisibility(View.VISIBLE);


                ft.replace(R.id.reqfrg,preqfragment,"preq fragement called").commit();

            }
        });
         return  v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}