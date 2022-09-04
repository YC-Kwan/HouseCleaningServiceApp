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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingBookingAdapter extends  RecyclerView.Adapter<PendingBookingAdapter.pendingViewHolder>{
    private Context mContext;
    private List<UserBooking> mUserBookings;
    private FirebaseFirestore db;
    private String mDate = "",mUsername = "",mAddress= "", mService="",mPhoneNo= "",mtime= "",price;
    private int mPrice;
    private String post_key = "";

    public PendingBookingAdapter( List<UserBooking> mUserBooking, Context c){
        mUserBookings= mUserBooking;
        mContext = c;
    }
    @NonNull
    @Override
    public PendingBookingAdapter.pendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.pending_booking_row, parent, false);
        return new pendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingBookingAdapter.pendingViewHolder holder, int position) {
        holder.tvDate.setText(mUserBookings.get(position).getDate());
        holder.tvUsername.setText(mUserBookings.get(position).getUsername());
        holder.tvAddress.setText(mUserBookings.get(position).getAddress());
        holder.tvService.setText(mUserBookings.get(position).getService());
        holder.tvPhoneNo.setText(mUserBookings.get(position).getPhoneNo());
        holder.tvPrice.setText("RM " + mUserBookings.get(position).getPrice());
        holder.tvTime.setText(mUserBookings.get(position).getTime() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_key = mUserBookings.get(position).getId();
                mDate= mUserBookings.get(position).getDate();
                mUsername= mUserBookings.get(position).getUsername();
                mAddress= mUserBookings.get(position).getAddress();
                mService= mUserBookings.get(position).getService();
                mPhoneNo= mUserBookings.get(position).getPhoneNo();
                mPrice= mUserBookings.get(position).getPrice();
                mtime=mUserBookings.get(position).getTime();
                updateData();
            }
        });
    }

    private void updateData() {
        AlertDialog.Builder myDialog= new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.complete_layout, null);

        myDialog.setView(mView);
        final  AlertDialog dialog = myDialog.create();

        final TextView mtvDate = mView.findViewById(R.id.history_date);
        final TextView mtvUsername = mView.findViewById(R.id.history_username);
        final TextView mtvAddress = mView.findViewById(R.id.history_address);
        final TextView mtvphoneNo = mView.findViewById(R.id.history_phoneNo);
        final TextView mtvService = mView.findViewById(R.id.history_service);
        final TextView mtvPrice = mView.findViewById(R.id.history_price);
        final TextView mtvTime = mView.findViewById(R.id.history_time);
        final Button btnComplete = mView.findViewById(R.id.btnComplete);

        mtvDate.setText(String.valueOf(mDate));
        mtvUsername.setText(String.valueOf(mUsername));
        mtvAddress.setText(String.valueOf(mAddress));
        mtvphoneNo.setText(String.valueOf(mPhoneNo));
        mtvService.setText(String.valueOf(mService));
        mtvPrice.setText(String.valueOf(mPrice));
        mtvTime.setText(String.valueOf(mtime));


        db = FirebaseFirestore.getInstance();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDate = mtvDate.getText().toString();
                mUsername = mtvUsername.getText().toString();
                mAddress = mtvAddress.getText().toString();
                mPhoneNo = mtvphoneNo.getText().toString();
                mService = mtvService.getText().toString();
                price = mtvPrice.getText().toString();
                mtime = mtvTime.getText().toString();

                db.collection("User Booking").document(post_key).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(mContext,AdminViewBooking.class);
                            mContext.startActivity(intent);
                        }
                    }
                });

                Map<String, Object> AddCompletedBooking = new HashMap<>();
                AddCompletedBooking.put("Cleaning Service", mService);
                AddCompletedBooking.put("Username", mUsername);
                AddCompletedBooking.put("Date", mDate);
                AddCompletedBooking.put("Address", mAddress);
                AddCompletedBooking.put("PhoneNo", mPhoneNo);
                AddCompletedBooking.put("Price", price + "");

                db.collection("Complete booking")
                        .add(AddCompletedBooking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(view.getContext(),"Updated successfully!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }

                        });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mUserBookings.size();
    }

    public class pendingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvService, tvPrice, tvTime, tvDate,tvAddress,tvPhoneNo,tvUsername;
        private Button btnComplete;

        public pendingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.history_date);
            tvUsername = itemView.findViewById(R.id.history_username);
            tvAddress = itemView.findViewById(R.id.history_address);
            tvPhoneNo = itemView.findViewById(R.id.history_phoneNo);
            tvService = itemView.findViewById(R.id.history_service);
            tvPrice = itemView.findViewById(R.id.history_price);
            tvTime = itemView.findViewById(R.id.history_time);
        }
    }
}
