package com.example.houseservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectYearAdapter extends RecyclerView.Adapter<SelectYearAdapter.SelectYearViewHolder>  {

    List<Year> mYears;
    private Context mContext;

    public SelectYearAdapter(Context c, List<Year> years) {
        mContext = c;
        mYears = years;
    }

    @NonNull
    @Override
    public SelectYearAdapter.SelectYearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.year_row, parent, false);
        return new SelectYearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectYearAdapter.SelectYearViewHolder holder, int position) {
        holder.tv_year.setText(mYears.get(position).getYear());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Dashboard.class);
                intent.putExtra("Year", mYears.get(position).getYear());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mYears.size();
    }
    public class SelectYearViewHolder extends RecyclerView.ViewHolder{
        TextView tv_year;

        public SelectYearViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_year = itemView.findViewById(R.id.selectYear);
        }
    }
}
