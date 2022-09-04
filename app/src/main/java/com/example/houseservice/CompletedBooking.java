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
 * Use the {@link CompletedBooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedBooking extends Fragment {
    private List<UserBooking> mUserBookings;
    private RecyclerView recyclerView;
    private CompletedBookingAdapter mCompletedBookingAdapter;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_completed_booking, container, false);

        recyclerView = v.findViewById(R.id.completeRV);

        db = FirebaseFirestore.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mUserBookings = new ArrayList<>();
        mCompletedBookingAdapter  = new CompletedBookingAdapter(mUserBookings, getContext());
        recyclerView.setAdapter(mCompletedBookingAdapter);

        getHistory();

        return v;
    }

    private void getHistory(){
        db.collection("Complete booking").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

                        int price = Integer.parseInt(book_price);

                        UserBooking userBooking = new UserBooking(id,username,cleaning_service,address,null,book_date, phoneNo, price,0);

                        mUserBookings.add(userBooking);
                    }
                }
                mCompletedBookingAdapter.notifyDataSetChanged();
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

    public CompletedBooking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedBooking.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedBooking newInstance(String param1, String param2) {
        CompletedBooking fragment = new CompletedBooking();
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