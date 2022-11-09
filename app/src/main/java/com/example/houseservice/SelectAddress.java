package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class SelectAddress extends AppCompatActivity {
    private TextView tvDate;
    private List<Address> mAddress;
    private RecyclerView recyclerView;
    private SelectAddressAdapter mSelectAddressAdapter;
    private FirebaseFirestore db;
    private String userId;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        tvDate = findViewById(R.id.tvDate);
        recyclerView = findViewById(R.id.address_recyclerView);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");
        int month = intent.getIntExtra("Month", 0);
        int year = intent.getIntExtra("Year", 0);

        String months = String.valueOf(month);
        String years = String.valueOf(year);

        tvDate.setText(date);

        //Set the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        mAddress = new ArrayList<>();

        //After finish get all the data, the mRoom object will put into the adapter which to set into the recyclerView
        mSelectAddressAdapter = new SelectAddressAdapter(this, mAddress, date,months,years);
        recyclerView.setAdapter(mSelectAddressAdapter);

        getAddress();
    }

    private void getAddress(){
        db.collection("Users").document(userId).collection("Address").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mAddress.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String address = documentSnapshot.getString("Address");

                        Address  addresses = new Address (id, address);

                        mAddress.add(addresses);
                    }
                }

                mSelectAddressAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}