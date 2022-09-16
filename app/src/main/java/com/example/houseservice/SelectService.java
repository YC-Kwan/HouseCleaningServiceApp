package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SelectService extends AppCompatActivity {

    private TextView tvAddress, tvDate;
    private String address, date,month;
    private List<CleaningService> mCleaningService;
    private RecyclerView recyclerView;
    private SelectServiceAdapter mSelectServiceAdapter;
    private FirebaseFirestore db;
    private String userId;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        tvAddress = findViewById(R.id.tvAddress);
        tvDate = findViewById(R.id.tvDate);
        recyclerView = findViewById(R.id.selectServiceRV);

        db = FirebaseFirestore.getInstance();

        address = getIntent().getStringExtra("Address");
        date = getIntent().getStringExtra("Date");
        month = getIntent().getStringExtra("Month");

        tvAddress.setText("Address: "+ address);
        tvDate.setText(date);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        mCleaningService = new ArrayList<>();

        //After finish get all the data, the mRoom object will put into the adapter which to set into the recyclerView
        mSelectServiceAdapter = new SelectServiceAdapter(this, mCleaningService, date,address,month);
        recyclerView.setAdapter(mSelectServiceAdapter);

        getService();
    }

    private void getService(){
        db.collection("Cleaning Service").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mCleaningService.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String cleaningService = documentSnapshot.getString("Cleaning Service");

                        CleaningService  cleaningServices = new CleaningService (id, cleaningService);

                        mCleaningService.add(cleaningServices);
                    }
                }

                mSelectServiceAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}