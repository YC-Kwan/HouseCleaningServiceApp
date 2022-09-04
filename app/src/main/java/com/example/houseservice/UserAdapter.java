package com.example.houseservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {
    Context mContext;
    List<Users> mUsers;


    public UserAdapter(List<Users> mUser, Context context) {
        this.mUsers = mUser;
        this.mContext= context;
    }

    @NonNull
    @Override
    public UserAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_row, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Users usernames = mUsers.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(usernames.getUid())){
            holder.itemView.setVisibility(View.GONE);
        }
        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(usernames.getUid())) {
            mUsers.add(usernames);
        }

        final String Username = usernames.getName();

        holder.TvName.setText(Username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("name", usernames.getName());
                intent.putExtra("uid", usernames.getUid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class Viewholder extends  RecyclerView.ViewHolder {

        TextView TvName;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            TvName = itemView.findViewById(R.id.tvName);
        }
    }
}
