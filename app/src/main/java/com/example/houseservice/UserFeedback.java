package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFeedback extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    List<Feedback> mFeedbacks;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private String userId,username;
    private FeedbackAdapter mFeedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        fab = findViewById(R.id.fab);


        recyclerView = findViewById(R.id.feedback_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mFeedbacks = new ArrayList<>();
        mFeedbackAdapter  = new FeedbackAdapter(mFeedbacks, UserFeedback.this);
        recyclerView.setAdapter(mFeedbackAdapter);

        DocumentReference getUsername = fStore.collection("Users").document(userId);
        getUsername.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username= documentSnapshot.getString("Username");
            }
        });

        getFeedback();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserFeedbacks.class));
            }
        });
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
                        String img= documentSnapshot.getString("Image");

                        Feedback feedback = new Feedback(id, feedbacks,date,null,img);

                        mFeedbacks.add(feedback);
                    }
                }

                mFeedbackAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

//    private void addFeedbacks() {
//
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View myView = inflater.inflate(R.layout.input_feedback, null);
//        myDialog.setView(myView);
//
//        final  AlertDialog dialog = myDialog.create();
//        dialog.setCancelable(false);
//
//        final EditText editFeedback = myView.findViewById(R.id.editFeedback);
//        final Button cancel = myView.findViewById(R.id.cancel);
//        final  Button post = myView.findViewById(R.id.post);
//
//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String feedbacks = editFeedback.getText().toString();
//                if (TextUtils.isEmpty(feedbacks)) {
//                    editFeedback.setError("Feedback is required!");
//                    editFeedback.requestFocus();
//                    return;
//                }
//
//                else {
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    Calendar cal = Calendar.getInstance();
//                    String date = dateFormat.format(cal.getTime());
//
//                    Map<String, Object> AddFeedback = new HashMap<>();
//
//                    AddFeedback.put("Created time", Calendar.getInstance().getTime().toString());
//                    AddFeedback.put("Feedback",editFeedback.getText().toString());
//                    AddFeedback.put("Date",date);
//                    AddFeedback.put("Username",username);
//
//
//                    fStore.collection("Feedback")
//                            .add(AddFeedback)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Toast.makeText(UserFeedback.this,"Feedback has been posted successfully!", Toast.LENGTH_LONG).show();
//                                    getFeedback();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//
//                                }
//
//                            });
//                    dialog.dismiss();
//                }
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}