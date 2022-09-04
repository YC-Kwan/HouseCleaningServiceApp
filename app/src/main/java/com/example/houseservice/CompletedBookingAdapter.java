package com.example.houseservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompletedBookingAdapter extends  RecyclerView.Adapter<CompletedBookingAdapter.completeViewHolder>{
    private Context mContext;
    private List<UserBooking> mUserBookings;

    public CompletedBookingAdapter( List<UserBooking> mUserBooking, Context c){
        mUserBookings= mUserBooking;
        mContext = c;
    }

    @NonNull
    @Override
    public CompletedBookingAdapter.completeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.complete_booking_row, parent, false);
        return new completeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedBookingAdapter.completeViewHolder holder, int position) {
        holder.tvDate.setText(mUserBookings.get(position).getDate());
        holder.tvUsername.setText(mUserBookings.get(position).getUsername());
        holder.tvAddress.setText(mUserBookings.get(position).getAddress());
        holder.tvService.setText(mUserBookings.get(position).getService());
        holder.tvPhoneNo.setText(mUserBookings.get(position).getPhoneNo());
        holder.tvPrice.setText("RM " + mUserBookings.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mUserBookings.size();
    }

    public class completeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvService, tvPrice, tvDate,tvAddress,tvPhoneNo,tvUsername;

        public completeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.history_date);
            tvUsername = itemView.findViewById(R.id.history_username);
            tvAddress = itemView.findViewById(R.id.history_address);
            tvPhoneNo = itemView.findViewById(R.id.history_phoneNo);
            tvService = itemView.findViewById(R.id.history_service);
            tvPrice = itemView.findViewById(R.id.history_price);

        }

    }
}
