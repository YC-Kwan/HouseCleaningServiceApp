package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String ReceiverUID, ReceiverName, SenderUID;
    TextView receiverName;
    private FirebaseFirestore fStore;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public static String sName;
    public static String rName;
    CardView sendBtn;
    EditText edtMessage;

    String senderRoom, receiverRoom;
    RecyclerView messageAdapter;
    ArrayList<Messages> messagesArrayList;

    MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ReceiverName=getIntent().getStringExtra("name");
        ReceiverUID=getIntent().getStringExtra("uid");

        messagesArrayList= new ArrayList<>();

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance("https://houseservice-b6345-default-rtdb.asia-southeast1.firebasedatabase.app/");

        receiverName=findViewById(R.id.receiverName);
        receiverName.setText(ReceiverName);
        sendBtn=findViewById(R.id.sendBtn);
        edtMessage=findViewById(R.id.edtMessage);

        messageAdapter=findViewById(R.id.messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        adapter=new MessagesAdapter(ChatActivity.this, messagesArrayList);
        messageAdapter.setAdapter(adapter);

        SenderUID = firebaseAuth.getUid();

        senderRoom=SenderUID+ReceiverUID;
        receiverRoom=ReceiverUID+SenderUID;



        DatabaseReference reference = database.getReference().child("Users").child(firebaseAuth.getUid());
        DatabaseReference chatReference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sName=snapshot.child("name").getValue().toString();
                rName=ReceiverName;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtMessage.getText().toString();

                Calendar c = Calendar.getInstance();

                SimpleDateFormat now = new SimpleDateFormat("dd/MM/yyyy");
                String currDate=now.format(c.getTime());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                String currTime=time.format(c.getTime());

                String dateTime= currDate+" "+currTime;

                if(message.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter valid message", Toast.LENGTH_LONG).show();
                    return;
                }
                edtMessage.setText("");
                Date date = new Date();

                Messages messages = new Messages(message,SenderUID,date.getTime(),dateTime);
                database=FirebaseDatabase.getInstance("https://houseservice-b6345-default-rtdb.asia-southeast1.firebasedatabase.app/");
                database.getReference().child("chats")
                       .child(senderRoom)
                       .child("messages")
                       .push()
                       .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       database.getReference().child("chats")
                               .child(receiverRoom)
                               .child("messages")
                               .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {

                           }
                       });
                   }
               });

            }
        });

    }
}