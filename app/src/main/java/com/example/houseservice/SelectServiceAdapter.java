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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectServiceAdapter extends RecyclerView.Adapter<SelectServiceAdapter.SelectServiceViewHolder> {
    private Context mContext;
    private List<CleaningService> mCleaningService;
    private String date,address, month, year, cleaningSerivce;

    private Boolean checkYear;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SelectServiceAdapter(Context c, List<CleaningService> mCleaningServices, String d,String a,String m, String y) {
        mContext = c;
        mCleaningService = mCleaningServices;
        date = d;
        address=a;
        month = m;
        year = y;
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
                cleaningSerivce = mCleaningService.get(position).getService();
                AddYear();
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

    public void AddYear(){
        CollectionReference collectCalendar = db.collection("Order");
        collectCalendar.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                    String years = documentSnapshots.getId();

                    checkYear = false;

                    if (years.equals(year)) {
                        checkYear = true;
                        break;
                    }
                }
                createYear(checkYear);
            }
        })      //Get the data and pass to the next page.
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(mContext, SelectTimeSlot.class);
                            intent.putExtra("Service", cleaningSerivce);
                            intent.putExtra("Address", address);
                            intent.putExtra("Date", date);
                            intent.putExtra("Month", month);
                            intent.putExtra("Year", year);
                            mContext.startActivity(intent);
                        }
                    }
                });
    }

    public void createYear(Boolean check) {
        if (!check) {
            DocumentReference order = db.collection("Order").document(year).collection("Analytic").document("Total Order");
            Map<String, Object> Years = new HashMap<>();
            Years.put("m1", "0");
            Years.put("m2", "0");
            Years.put("m3", "0");
            Years.put("m4", "0");
            Years.put("m5", "0");
            Years.put("m6", "0");
            Years.put("m7", "0");
            Years.put("m8", "0");
            Years.put("m9", "0");
            Years.put("m10", "0");
            Years.put("m11", "0");
            Years.put("m12", "0");
            Years.put("totalorder", "0");
            Years.put("year", year);
            order.set(Years);

            DocumentReference orders = db.collection("Order").document(year);
            Map<String, Object> orderYear = new HashMap<>();
            orderYear.put("year", year);
            orders.set(orderYear);

            DocumentReference earn = db.collection("Order").document(year).collection("Analytic").document("Total Earn");
            Map<String, Object> earnYear = new HashMap<>();
            earnYear.put("m1", "0");
            earnYear.put("m2", "0");
            earnYear.put("m3", "0");
            earnYear.put("m4", "0");
            earnYear.put("m5", "0");
            earnYear.put("m6", "0");
            earnYear.put("m7", "0");
            earnYear.put("m8", "0");
            earnYear.put("m9", "0");
            earnYear.put("m10", "0");
            earnYear.put("m11", "0");
            earnYear.put("m12", "0");
            earnYear.put("totalearn", "0");
            earnYear.put("year", year);
            earn.set(earnYear);
        }
    }
}
