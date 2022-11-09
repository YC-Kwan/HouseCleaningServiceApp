package com.example.houseservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.cartViewHolder> {

    private Context mContext;
    private List<UserBooking> mUserBookings;
    FirebaseFirestore db;

    public CartAdapter( List<UserBooking> mUserBooking, Context c){
        mUserBookings= mUserBooking;
        mContext = c;
    }
    @NonNull
    @Override
    public CartAdapter.cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.history_row, parent, false);
        return new CartAdapter.cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.cartViewHolder holder, int position) {
        holder.tvDate.setText(mUserBookings.get(position).getDate());
        holder.tvAddress.setText(mUserBookings.get(position).getAddress());
        holder.tvService.setText(mUserBookings.get(position).getService());
        holder.tvPhoneNo.setText(mUserBookings.get(position).getPhoneNo());
        holder.tvPrice.setText("RM " + mUserBookings.get(position).getPrice());
        holder.tvTime.setText(mUserBookings.get(position).getTime() + "");

        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserPayment.class);
                intent.putExtra("cartID", mUserBookings.get(position).getId());
                intent.putExtra("price", mUserBookings.get(position).getPrice());
                mContext.startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        holder.delCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("User Booking").document(mUserBookings.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(v.getContext(),"Booking cart deleted successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext,UserCart.class);
                            mContext.startActivity(intent);
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserBookings.size();
    }

    public class cartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvService, tvPrice, tvTime, tvDate,tvAddress,tvPhoneNo,tvHour;
        private Button btnPay;
        private ImageView delCart;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.history_date);
            tvAddress = itemView.findViewById(R.id.history_address);
            tvPhoneNo = itemView.findViewById(R.id.history_phoneNo);
            tvService = itemView.findViewById(R.id.history_service);
            tvPrice = itemView.findViewById(R.id.history_price);
            tvTime = itemView.findViewById(R.id.history_time);
            btnPay = itemView.findViewById(R.id.payBtn);
            delCart = itemView.findViewById(R.id.delCart);


        }

    }
}
