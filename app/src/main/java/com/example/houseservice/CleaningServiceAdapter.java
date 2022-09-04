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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CleaningServiceAdapter extends RecyclerView.Adapter<CleaningServiceAdapter.CleaningServiceViewHolder> {
    List<CleaningService> mCleaningService;
    private Context mContext;
    private String mService = "";
    private String post_key = "";
    FirebaseFirestore db;

    public CleaningServiceAdapter(List<CleaningService> mCleaningService, Context mContext) {
        this.mCleaningService = mCleaningService;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CleaningServiceAdapter.CleaningServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_cleaning_service_row, parent, false);
        return new CleaningServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CleaningServiceAdapter.CleaningServiceViewHolder holder, int position) {
        final CleaningService cleaningService = mCleaningService.get(position);

        final String cleaningServices =cleaningService.getService();

        holder.tvService.setText(cleaningServices);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_key = cleaningService.getId();
                mService = cleaningService.getService();
                updateData();
            }
        });
    }
    private void updateData() {
        AlertDialog.Builder myDialog= new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.update_cleaning_service, null);

        myDialog.setView(mView);
        final  AlertDialog dialog = myDialog.create();

        final EditText mServices = mView.findViewById(R.id.update_service);

        mServices.setText(String.valueOf(mService));

        Button btnDel = mView.findViewById(R.id.btnDelete);
        Button btnUpdate = mView.findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mService = mServices.getText().toString();

                if(mService.isEmpty()) {
                    Toast.makeText(view.getContext(),"Cleaning service is required!", Toast.LENGTH_LONG).show();
                }
                else {
                    CleaningService cleaningService = new CleaningService(post_key, mService);
                    db.collection("Cleaning Service").document(post_key)
                            .update(
                                    "Cleaning Service", cleaningService.getService()
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Updated successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext, AddCleaningService.class);
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
                db.collection("Cleaning Service").document(post_key).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(view.getContext(),"Deleted successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext,AddCleaningService.class);
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
        return mCleaningService.size();
    }

    class CleaningServiceViewHolder extends RecyclerView.ViewHolder{

        TextView tvService;

        public CleaningServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tvService);

        }
    }
}
