package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingHistory extends AppCompatActivity {

    private List<UserBooking> mUserBookings;
    private RecyclerView recyclerView;
    private BookingHistoryAdapter mBookingHistoryAdapter;
    private String userId;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerView = findViewById(R.id.history_recyclerView);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mUserBookings = new ArrayList<>();
        mBookingHistoryAdapter  = new BookingHistoryAdapter(mUserBookings, BookingHistory.this);
        recyclerView.setAdapter(mBookingHistoryAdapter);

        getHistory();
    }

    private void getHistory(){
        db.collection("Users").document(userId).collection("History").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mUserBookings.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String cleaning_service = documentSnapshot.getString("Cleaning Service");
                        String address = documentSnapshot.getString("Address");
                        String book_date = documentSnapshot.getString("Date");
                        String book_hour = documentSnapshot.getString("Hour");
                        String phoneNo = documentSnapshot.getString("PhoneNo");
                        String book_price = documentSnapshot.getString("Price");
                        String book_time = "";

                        int price = Integer.parseInt(book_price);
                        int hour_count = Integer.parseInt(book_hour);

                        for (int x=1; x<=hour_count; x++){
                            book_time += documentSnapshot.getString("Time " + x) + "\n";
                        }

                        UserBooking userBooking = new UserBooking(id,null,cleaning_service,address,book_time,book_date, phoneNo, price,hour_count);

                        mUserBookings.add(userBooking);
                    }
                }
                mBookingHistoryAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}