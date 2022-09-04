package com.example.houseservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdapterAddress  extends RecyclerView.Adapter<AdapterAddress.AddressListHolder>{
    List<Address> mAddresses;
    private Context mContext;
    private String mAddress = "";
    private String post_key = "";
    FirebaseFirestore db;
    private String userId;
    private FirebaseAuth fAuth;

    public AdapterAddress(List<Address> mAddresses, Context mContext) {
        this.mAddresses = mAddresses;
        this.mContext= mContext;
    }

    @NonNull
    @Override
    public AdapterAddress.AddressListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_address_row, parent, false);
        return new AddressListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddress.AddressListHolder holder, int position) {
        final Address Addresses = mAddresses.get(position);

        final String userAddress =Addresses.getAddress();
        holder.address.setText(userAddress);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_key = Addresses.getId();
                mAddress = Addresses.getAddress();
                updateData();
            }
        });
    }

    private void updateData() {
        AlertDialog.Builder myDialog= new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.update_address, null);

        myDialog.setView(mView);
        final  AlertDialog dialog = myDialog.create();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        final EditText mAddresses= mView.findViewById(R.id.update_address);

        mAddresses.setText(String.valueOf(mAddress));

        Button btnDel = mView.findViewById(R.id.btnDelete);
        Button btnUpdate = mView.findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddress = mAddresses.getText().toString();

                if (mAddress.isEmpty()) {
                    Toast.makeText(view.getContext(), "Address is required!", Toast.LENGTH_LONG).show();
                } else {
                    Address address = new Address(post_key, mAddress);
                    db.collection("Users").document(userId).collection("Address").document(post_key)
                            .update(
                                    "Address", address.getAddress()
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Updated successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext, ManageAddress.class);
                            mContext.startActivity(intent);

                        }
                    });
                    dialog.dismiss();
                }
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Users").document(userId).collection("Address").document(post_key).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(view.getContext(),"Deleted successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext,ManageAddress.class);
                            mContext.startActivity(intent);
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class AddressListHolder extends RecyclerView.ViewHolder{

        TextView address;

        public AddressListHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.user_address);
        }
    }

}
