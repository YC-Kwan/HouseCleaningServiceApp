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

public class ChatList extends AppCompatActivity {

    private RecyclerView mainUserRecyclerView;
    private UserAdapter adapter;
    private FirebaseFirestore fStore;
    List<Users> UsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        fStore=FirebaseFirestore.getInstance();

        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mainUserRecyclerView.setHasFixedSize(true);
        mainUserRecyclerView.setLayoutManager(linearLayoutManager);

        UsersList = new ArrayList<>();
        adapter  = new UserAdapter(UsersList, ChatList.this);
        mainUserRecyclerView.setAdapter(adapter);

        getUser();
    }

    private void getUser(){
        fStore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                UsersList.clear();

                for (int i=0; i<queryDocumentSnapshots.size();i++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);

                    if(documentSnapshot.exists()) {

                        String id = documentSnapshot.getId();
                        String name = documentSnapshot.getString("Username");

                        Users users = new Users(id, name,null);

                        UsersList.add(users);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}