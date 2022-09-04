package com.example.houseservice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingBooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingBooking extends Fragment {
    private List<UserBooking> mUserBookings;
    private RecyclerView recyclerView;
    private PendingBookingAdapter mPendingBookingAdapter;
    private String userId;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pending_booking, container, false);

        recyclerView = v.findViewById(R.id.pendingRV);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mUserBookings = new ArrayList<>();
        mPendingBookingAdapter  = new PendingBookingAdapter(mUserBookings, getContext());
        recyclerView.setAdapter(mPendingBookingAdapter);

        getHistory();

        return v;
    }

    private void getHistory(){
        db.collection("User Booking").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mUserBookings.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String cleaning_service = documentSnapshot.getString("Cleaning Service");
                        String username = documentSnapshot.getString("Username");
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

                        UserBooking userBooking = new UserBooking(id,username,cleaning_service,address,book_time,book_date, phoneNo, price,hour_count);

                        mUserBookings.add(userBooking);
                    }
                }
                mPendingBookingAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PendingBooking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingBooking.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingBooking newInstance(String param1, String param2) {
        PendingBooking fragment = new PendingBooking();
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