package com.example.houseservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.SelectAddressViewHolder>{

    private Context mContext;
    private List<Address> mAddress;
    private String date,month,year;

    public SelectAddressAdapter(Context c, List<Address> address, String d, String m, String y) {
        mContext = c;
        mAddress = address;
        date = d;
        month = m;
        year = y;
    }

    @NonNull
    @Override
    public SelectAddressAdapter.SelectAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.select_address_row, parent, false);
        return new SelectAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAddressAdapter.SelectAddressViewHolder holder, int position) {
        holder.tv_address.setText(mAddress.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SelectService.class);
                intent.putExtra("Address", mAddress.get(position).getAddress());
                intent.putExtra("Date", date);
                intent.putExtra("Month", month);
                intent.putExtra("Year", year);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddress.size();
    }
    public class SelectAddressViewHolder extends RecyclerView.ViewHolder{
        TextView tv_address;

        public SelectAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_address = itemView.findViewById(R.id.selectAddress);
        }
    }
}
