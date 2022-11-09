package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SelectYear extends AppCompatActivity {

    private List<Year> mYears;
    private RecyclerView recyclerView;
    private SelectYearAdapter mSelectYearAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        recyclerView = findViewById(R.id.year_recyclerView);

        db = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        mYears = new ArrayList<>();

        //After finish get all the data, the mRoom object will put into the adapter which to set into the recyclerView
        mSelectYearAdapter = new SelectYearAdapter(this, mYears);
        recyclerView.setAdapter(mSelectYearAdapter);

        getYear();
    }

    private void getYear(){
        db.collection("Order").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mYears.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String year = documentSnapshot.getString("year");

                        Year  years = new Year (id, year);
                        mYears.add(years);
                    }
                }
                mSelectYearAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}