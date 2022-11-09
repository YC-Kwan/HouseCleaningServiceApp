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
 * Use the {@link TotalOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalOrder extends Fragment {
    private String monthCounter1,monthCounter2,monthCounter3,monthCounter4,monthCounter5,monthCounter6,monthCounter7,monthCounter8,monthCounter9,
            monthCounter10,monthCounter11,monthCounter12, year;
    FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_total_order, container, false);

        db = FirebaseFirestore.getInstance();
        BarChart barChart = v.findViewById(R.id.OrderBarChart);
        TextView totalOrder = v.findViewById(R.id.totalOrder);

        year = getActivity().getIntent().getExtras().getString("Year");

        DocumentReference monthCounter = db.collection("Order").document(year).collection("Analytic").document("Total Order");
        monthCounter.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                monthCounter1= documentSnapshot.getString("m1");
                monthCounter2= documentSnapshot.getString("m2");
                monthCounter3= documentSnapshot.getString("m3");
                monthCounter4= documentSnapshot.getString("m4");
                monthCounter5= documentSnapshot.getString("m5");
                monthCounter6= documentSnapshot.getString("m6");
                monthCounter7= documentSnapshot.getString("m7");
                monthCounter8= documentSnapshot.getString("m8");
                monthCounter9= documentSnapshot.getString("m9");
                monthCounter10= documentSnapshot.getString("m10");
                monthCounter11= documentSnapshot.getString("m11");
                monthCounter12= documentSnapshot.getString("m12");
                totalOrder.setText("Total Order: "+documentSnapshot.getString("totalorder"));

                ArrayList<BarEntry> order = new ArrayList<>();
                order.add(new BarEntry(1, Long.parseLong(String.valueOf(monthCounter1))));
                order.add(new BarEntry(2, Long.parseLong(String.valueOf(monthCounter2))));
                order.add(new BarEntry(3, Long.parseLong(String.valueOf(monthCounter3))));
                order.add(new BarEntry(4, Long.parseLong(String.valueOf(monthCounter4))));
                order.add(new BarEntry(5, Long.parseLong(String.valueOf(monthCounter5))));
                order.add(new BarEntry(6, Long.parseLong(String.valueOf(monthCounter6))));
                order.add(new BarEntry(7, Long.parseLong(String.valueOf(monthCounter7))));
                order.add(new BarEntry(8, Long.parseLong(String.valueOf(monthCounter8))));
                order.add(new BarEntry(9, Long.parseLong(String.valueOf(monthCounter9))));
                order.add(new BarEntry(10, Long.parseLong(String.valueOf(monthCounter10))));
                order.add(new BarEntry(11, Long.parseLong(String.valueOf(monthCounter11))));
                order.add(new BarEntry(12, Long.parseLong(String.valueOf(monthCounter12))));

                BarDataSet barDataSet = new BarDataSet(order, "Total orders in months");
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(11f);

                YAxis rightYAxis = barChart.getAxisRight();
                rightYAxis.setEnabled(false);

                BarData barData = new BarData(barDataSet);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("Order Bar Chart ");
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

    public TotalOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalOrder newInstance(String param1, String param2) {
        TotalOrder fragment = new TotalOrder();
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