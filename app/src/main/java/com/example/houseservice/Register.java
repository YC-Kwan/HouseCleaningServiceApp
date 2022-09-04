package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseDatabase database;
    private Button btn_register;
    private EditText editEmail, editPassword,editUsername, editPhoneNo;
    private boolean valid = true;
    private String name, phone, email, password,userID;
    private  TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database=FirebaseDatabase.getInstance("https://houseservice-b6345-default-rtdb.asia-southeast1.firebasedatabase.app/");

        btn_register=findViewById(R.id.registeruser);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        editUsername = findViewById(R.id.editTextUserName);
        editPhoneNo = findViewById(R.id.editTextPhone);
        tvLogin = findViewById(R.id.logintext);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void registeruser(){
        name = editUsername.getText().toString().trim();
        phone = editPhoneNo.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editUsername.setError("Enter a name");
            editUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Enter a email");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Invalid email format");
            editEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone) ) {
            editPhoneNo.setError("Enter your phone number");
            editPhoneNo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter the password");
            editPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            editPassword.setError("Password too short, must at least 8 character");
            editPassword.requestFocus();
            return;
        }

        if(valid){
            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            userID = fAuth.getCurrentUser().getUid();
                            DatabaseReference reference = database.getReference().child("Users").child(userID);
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Username", name);
                            userInfo.put("Email", email);
                            userInfo.put("PhoneNo", phone);
                            userInfo.put("History", "0");
                            userInfo.put("UserType","User");
                            documentReference.set(userInfo);

                            Users users= new Users(userID, name,email);
                            reference.setValue(users);

                            Toast.makeText(getApplicationContext(),"User has been registered successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),User.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to register! Try again!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}