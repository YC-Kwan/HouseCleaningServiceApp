package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class Profile extends AppCompatActivity {
    private TextView profileName,profilePhone,profileEmail;
    private Button profileUpdate,changePass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    private String userId;
    String mUsername, mPhone,mEmail;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileUsername);
        profilePhone = findViewById(R.id.profilePhone);
        profileEmail = findViewById(R.id.profileEmail);
        profileUpdate=findViewById(R.id.profileUpdate);
        changePass=findViewById(R.id.changePass);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        user = firebaseAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance("https://houseservice-b6345-default-rtdb.asia-southeast1.firebasedatabase.app/");



        DocumentReference documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userName = documentSnapshot.getString("Username");
                String phoneNo = documentSnapshot.getString("PhoneNo");
                String email = documentSnapshot.getString("Email");

                profileName.setText(String.valueOf(userName));
                profilePhone.setText(String.valueOf(phoneNo));
                profileEmail.setText(String.valueOf(email));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsername = profileName.getText().toString();
                mPhone = profilePhone.getText().toString();
                mEmail = profileEmail.getText().toString();

                if (TextUtils.isEmpty(mUsername)) {
                    profileName.setError("Username is required");
                    profileName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mPhone)) {
                    profilePhone.setError("Phone number is required");
                    profilePhone.requestFocus();
                    return;
                }

                else {
                    DocumentReference updateProfile = db.collection("Users").document(userId);
                    updateProfile.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            updateProfile.update("Username", mUsername);
                            updateProfile.update("PhoneNo", mPhone + "");

                            Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    DatabaseReference reference = database.getReference().child("Users").child(userId).child("name");
                    reference.setValue(mUsername);
                }
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassDialog();
            }
        });
    }
    private void showPassDialog(){
        View v = LayoutInflater.from(Profile.this).inflate(R.layout.change_password, null);
        EditText currentPassword = v.findViewById(R.id.editTextCurrentPassword);
        EditText newPassword = v.findViewById(R.id.editTextNewPassword);
        EditText comfirmPassword = v.findViewById(R.id.editTextCfnPassword);
        Button btn_done = v.findViewById(R.id.button_save);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setView(v); //set view to dialog

        AlertDialog dialog = builder.create();
        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate the data
                String current_password = currentPassword.getText().toString().trim();
                String new_password = newPassword.getText().toString().trim();
                String comfirm_password = comfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(current_password)) {
                    currentPassword.setError("Enter the current password");
                    currentPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(new_password)) {
                    newPassword.setError("Enter the new password");
                    newPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(comfirm_password)) {
                    comfirmPassword.setError("Enter the new password again");
                    comfirmPassword.requestFocus();
                    return;
                }

                if (!comfirm_password.equals(new_password)) {
                    comfirmPassword.setError("Password not matching");
                    comfirmPassword.requestFocus();
                    return;
                }

                dialog.dismiss();
                changePassword(current_password, new_password);
            }
        });
    }

    private void changePassword(String currentPassword, final String newPassword){
        //verify the user
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //after verify ,update the password
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profile.this, "Password change successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}