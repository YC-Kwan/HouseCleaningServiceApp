package com.example.houseservice;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotalEarn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalEarn extends Fragment {
    private String monthEarns1,monthEarns2,monthEarns3,monthEarns4,monthEarns5,monthEarns6,monthEarns7,monthEarns8,monthEarns9,
            monthEarns10,monthEarns11,monthEarns12;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_total_earn, container, false);

        db = FirebaseFirestore.getInstance();
        BarChart barChart = v.findViewById(R.id.EarnBarChart);
        TextView totalOrder = v.findViewById(R.id.totalEarn);

        DocumentReference monthCounter = db.collection("Order").document("uv5uyCIl0ZGfytvqnNv2");
        monthCounter.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                monthEarns1= documentSnapshot.getString("m1");
                monthEarns2= documentSnapshot.getString("m2");
                monthEarns3= documentSnapshot.getString("m3");
                monthEarns4= documentSnapshot.getString("m4");
                monthEarns5= documentSnapshot.getString("m5");
                monthEarns6= documentSnapshot.getString("m6");
                monthEarns7= documentSnapshot.getString("m7");
                monthEarns8= documentSnapshot.getString("m8");
                monthEarns9= documentSnapshot.getString("m9");
                monthEarns10= documentSnapshot.getString("m10");
                monthEarns11= documentSnapshot.getString("m11");
                monthEarns12= documentSnapshot.getString("m12");
                totalOrder.setText("Total Earn: RM"+documentSnapshot.getString("totalearn"));

                ArrayList<BarEntry> earn = new ArrayList<>();
                earn.add(new BarEntry(1, Long.parseLong(String.valueOf(monthEarns1))));
                earn.add(new BarEntry(2, Long.parseLong(String.valueOf(monthEarns2))));
                earn.add(new BarEntry(3, Long.parseLong(String.valueOf(monthEarns3))));
                earn.add(new BarEntry(4, Long.parseLong(String.valueOf(monthEarns4))));
                earn.add(new BarEntry(5, Long.parseLong(String.valueOf(monthEarns5))));
                earn.add(new BarEntry(6, Long.parseLong(String.valueOf(monthEarns6))));
                earn.add(new BarEntry(7, Long.parseLong(String.valueOf(monthEarns7))));
                earn.add(new BarEntry(8, Long.parseLong(String.valueOf(monthEarns8))));
                earn.add(new BarEntry(9, Long.parseLong(String.valueOf(monthEarns9))));
                earn.add(new BarEntry(10, Long.parseLong(String.valueOf(monthEarns10))));
                earn.add(new BarEntry(11, Long.parseLong(String.valueOf(monthEarns11))));
                earn.add(new BarEntry(12, Long.parseLong(String.valueOf(monthEarns12))));

                BarDataSet barDataSet = new BarDataSet(earn, "Total earns in months");
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(11f);

                YAxis rightYAxis = barChart.getAxisRight();
                rightYAxis.setEnabled(false);

                BarData barData = new BarData(barDataSet);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("Earn Bar Chart ");
                barChart.animateY(2000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TotalEarn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalEarn.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalEarn newInstance(String param1, String param2) {
        TotalEarn fragment = new TotalEarn();
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
    }

}