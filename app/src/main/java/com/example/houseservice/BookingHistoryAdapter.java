package com.example.houseservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingHistoryAdapter extends  RecyclerView.Adapter<BookingHistoryAdapter.historyViewHolder>{
    private Context mContext;
    private List<UserBooking> mUserBookings;

    public BookingHistoryAdapter( List<UserBooking> mUserBooking, Context c){
        mUserBookings= mUserBooking;
        mContext = c;
    }
    @NonNull
    @Override
    public BookingHistoryAdapter.historyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.history_row, parent, false);
        return new historyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryAdapter.historyViewHolder holder, int position) {
        holder.tvDate.setText(mUserBookings.get(position).getDate());
        holder.tvAddress.setText(mUserBookings.get(position).getAddress());
        holder.tvService.setText(mUserBookings.get(position).getService());
        holder.tvPhoneNo.setText(mUserBookings.get(position).getPhoneNo());
        holder.tvPrice.setText("RM " + mUserBookings.get(position).getPrice());
        holder.tvTime.setText(mUserBookings.get(position).getTime() + "");

    }

    @Override
    public int getItemCount() {
        return mUserBookings.size();
    }

    public class historyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvService, tvPrice, tvTime, tvDate,tvAddress,tvPhoneNo,tvHour;

        public historyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.history_date);
            tvAddress = itemView.findViewById(R.id.history_address);
            tvPhoneNo = itemView.findViewById(R.id.history_phoneNo);
            tvService = itemView.findViewById(R.id.history_service);
            tvPrice = itemView.findViewById(R.id.history_price);
            tvTime = itemView.findViewById(R.id.history_time);

        }

    }
}
