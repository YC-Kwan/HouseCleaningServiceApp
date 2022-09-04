package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFeedbacks extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private String userId,username;
    private ImageView imgFeedback;
    private static final int Image=1;
    private Uri ImageUri;
    private String feedbackID, downloadUrl,userID;
    private StorageReference sr;
    private FirebaseAuth fAuth;
    private EditText editFeedback;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedbacks);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        sr= FirebaseStorage.getInstance().getReference().child("Images");

        editFeedback = findViewById(R.id.editFeedback);
        post = findViewById(R.id.post);
        imgFeedback=findViewById(R.id.imgFeedback);

        DocumentReference getUsername = fStore.collection("Users").document(userId);
        getUsername.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username= documentSnapshot.getString("Username");
            }
        });


        imgFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddImage();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateField();
            }
        });

    }

    private void AddImage(){
        Intent image = new Intent();
        image.setAction(Intent.ACTION_GET_CONTENT);
        image.setType("image/*");
        startActivityForResult(image,Image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Image && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            imgFeedback.setImageURI(ImageUri);

        }
    }

    private void StoreFeedback(){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat now = new SimpleDateFormat("MM-dd-yyyy");
        String currDate=now.format(c.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String currTime=time.format(c.getTime());

        feedbackID= currDate+currTime;

        StorageReference filePath= sr.child(ImageUri.getLastPathSegment() + feedbackID+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(UserFeedbacks.this, message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadUrl=task.getResult().toString();
                            addFeedback();
                        }
                    }
                });
            }
        });
    }

    private void validateField(){
        String feedbacks = editFeedback.getText().toString();

        if (TextUtils.isEmpty(feedbacks)) {
            editFeedback.setError("Feedback is required!");
            editFeedback.requestFocus();
            return;
        }
        if( ImageUri==null){
            Toast.makeText(UserFeedbacks.this, "Image is required!", Toast.LENGTH_SHORT).show();
        }

        else {
            StoreFeedback();
        }
    }

    private void addFeedback(){
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            Map<String, Object> AddFeedback = new HashMap<>();


            AddFeedback.put("Created time", Calendar.getInstance().getTime().toString());
            AddFeedback.put("Feedback",editFeedback.getText().toString());
            AddFeedback.put("Date",date);
            AddFeedback.put("Username",username);
            AddFeedback.put("Image",downloadUrl);


            fStore.collection("Feedback")
                    .add(AddFeedback)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(UserFeedbacks.this,"Feedback has been posted successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),UserFeedback.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }

                    });
    }


}