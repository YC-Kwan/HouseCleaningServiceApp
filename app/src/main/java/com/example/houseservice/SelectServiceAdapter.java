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

import java.util.List;

public class SelectServiceAdapter extends RecyclerView.Adapter<SelectServiceAdapter.SelectServiceViewHolder> {
    private Context mContext;
    private List<CleaningService> mCleaningService;
    private String date,address, month;

    public SelectServiceAdapter(Context c, List<CleaningService> mCleaningServices, String d,String a,String m) {
        mContext = c;
        mCleaningService = mCleaningServices;
        date = d;
        address=a;
        month = m;
    }
    @NonNull
    @Override
    public SelectServiceAdapter.SelectServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.select_service_row, parent, false);
        return new SelectServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectServiceAdapter.SelectServiceViewHolder holder, int position) {
        holder.tv_service.setText(mCleaningService.get(position).getService());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SelectTimeSlot.class);
                intent.putExtra("Service", mCleaningService.get(position).getService());
                intent.putExtra("Address", address);
                intent.putExtra("Date", date);
                intent.putExtra("Month", month);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCleaningService.size();
    }

    public class SelectServiceViewHolder extends RecyclerView.ViewHolder{
        TextView tv_service;

        public SelectServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_service = itemView.findViewById(R.id.selectService);
        }
    }
}
