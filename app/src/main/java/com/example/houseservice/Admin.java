package com.example.houseservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {
    private CardView card_chat,card_service,card_booking,card_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        card_chat=findViewById(R.id.card_userChat);
        card_service=findViewById(R.id.addService);
        card_booking=findViewById(R.id.card_userBook);
        card_feedback=findViewById(R.id.card_adminFeedback);

        card_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AddCleaningService.class));
            }
        });

        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),ChatList.class));
            }
        });

        card_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AdminViewBooking.class));
            }
        });

        card_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AdminFeedback.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                startActivity(new Intent(getApplicationContext(),Profile.class));
                return true;
            case R.id.item2:
                startActivity(new Intent(getApplicationContext(), SelectYear.class));
                return true;
            case R.id.item3:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}