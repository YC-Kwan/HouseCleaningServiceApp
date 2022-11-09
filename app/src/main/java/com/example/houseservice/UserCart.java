package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserCart extends AppCompatActivity {

    private List<UserBooking> mUserBookings;
    private RecyclerView recyclerView;
    private CartAdapter mCartAdapter;
    private String userId;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        recyclerView = findViewById(R.id.cart_recyclerView);

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
        mCartAdapter  = new CartAdapter(mUserBookings, UserCart.this);
        recyclerView.setAdapter(mCartAdapter);

        getCart();
    }

    private void getCart(){
        db.collection("User Booking").whereEqualTo("Status", "unpaid").whereEqualTo("UserID", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                mCartAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}