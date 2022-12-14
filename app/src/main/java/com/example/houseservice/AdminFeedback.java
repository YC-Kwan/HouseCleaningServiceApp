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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminFeedback extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    List<Feedback> mFeedbacks;
    private RecyclerView recyclerView;
    private AdminFeedbackAdapter mAdminFeedbackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.adminFeedback_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mFeedbacks = new ArrayList<>();
        mAdminFeedbackAdapter  = new AdminFeedbackAdapter(mFeedbacks, AdminFeedback.this);
        recyclerView.setAdapter(mAdminFeedbackAdapter);

        getFeedback();
    }

    private void getFeedback(){
        fStore.collection("Feedback").orderBy("Created time", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mFeedbacks.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String feedbacks = documentSnapshot.getString("Feedback");
                        String date= documentSnapshot.getString("Date");
                        String username=documentSnapshot.getString("Username");
                        String img=documentSnapshot.getString("Image");

                        Feedback feedback = new Feedback(id, feedbacks,date,username,img);

                        mFeedbacks.add(feedback);
                    }
                }
                mAdminFeedbackAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}