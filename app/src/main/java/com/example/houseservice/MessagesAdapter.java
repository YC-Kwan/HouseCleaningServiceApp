package com.example.houseservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.houseservice.ChatActivity.rName;
import static com.example.houseservice.ChatActivity.sName;

public class MessagesAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Messages> messagesArrayList;
    int ITEM_SEND=1;
    int ITEM_RECEIVE =2;

    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new SenderViewHolder(view);
        }else{
            View view= LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new RecieverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.txtmessage.setText(messages.getMessage());
            viewHolder.txtname.setText(sName);
            viewHolder.txtSenderDate.setText(messages.getDateTime());
        }else {
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
            viewHolder.txtmessage.setText(messages.getMessage());
            viewHolder.txtname.setText(rName);
            viewHolder.txtReceiverDate.setText(messages.getDateTime());
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages messages = messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))
        {
            return ITEM_SEND;
        }else {
            return ITEM_RECEIVE;
        }
    }

    class SenderViewHolder extends  RecyclerView.ViewHolder{
        TextView txtmessage,txtname,txtSenderDate;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.senderName);
            txtmessage=itemView.findViewById(R.id.senderMessage);
            txtSenderDate=itemView.findViewById(R.id.senderDate);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView txtmessage,txtname,txtReceiverDate;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.receiverName);
            txtmessage=itemView.findViewById(R.id.receiverMessage);
            txtReceiverDate=itemView.findViewById(R.id.receiverDate);

        }
    }
}
