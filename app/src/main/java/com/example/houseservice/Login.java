package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private boolean valid=true;
    private Button mSignIn;
    private EditText editEmail, editPassword;
    private TextView tvForgetPass, signintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mSignIn = findViewById(R.id.signIn);
        editEmail=findViewById(R.id.email);
        editPassword=findViewById(R.id.password);
        tvForgetPass=findViewById(R.id.tvForgetPass);
        signintext=findViewById(R.id.registertext);


        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                userLogin();
            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetEmail = new EditText(Login.this);

                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(Login.this);
                passwordResetDialog.setTitle("Forget Password? ");
                passwordResetDialog.setMessage("Enter your email to reset your password");
                resetEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userEmail = resetEmail.getText().toString().trim();

                        if (userEmail.equals("")){
                            Toast.makeText(Login.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            fAuth.sendPasswordResetEmail(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close
                    }
                });
                passwordResetDialog.create().show();
            }
        });

    }
    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email Required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password Required");
            editPassword.requestFocus();
            return;
        }
        if(password.length()<8){
            editPassword.setError("Min password length should be 8 characters!");
            editPassword.requestFocus();
            return;
        }

        if (valid) {
            fAuth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(), "Login successfully!", Toast.LENGTH_LONG).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Please Try Again!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void checkUserAccessLevel(String uid)
    {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess:" +documentSnapshot.getData());

                if(documentSnapshot.getString("UserType").equals("Admin")){
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                }
                if(documentSnapshot.getString("UserType").equals("User")){
                    startActivity(new Intent(getApplicationContext(),User.class));
                }
            }
        });
    }
    public void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("UserType").equals("Admin")){
                        startActivity(new Intent(getApplicationContext(),Admin.class));
                    }

                    if(documentSnapshot.getString("UserType").equals("User")){
                        startActivity(new Intent(getApplicationContext(),User.class));
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), Account.class));
                }
            });
        }
    }
}