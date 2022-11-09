package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectTimeSlot extends AppCompatActivity {
    private TextView tvAddress, tvDate, tvService, tvPrice;

    private String address, date, cleaningService, userID, phoneNo,username,month, year;

    private int price = 0, hour = 0, historyNum;

    private int count10AM = 0, count11AM = 0, count12PM = 0, count1PM = 0, count2PM = 0, count3PM = 0, count4PM = 0,
            count5PM = 0;

    private Button btn_10_11,btn_11_12,btn_12_1,btn_1_2,btn_2_3,btn_3_4,btn_4_5,btn_5_6;

    private Button btnBook;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_slot);

        tvAddress = findViewById(R.id.tvAddress);
        tvDate = findViewById(R.id.tvDate);
        tvService = findViewById(R.id.tvService);
        tvPrice = findViewById(R.id.tvPrice);

        address = getIntent().getStringExtra("Address");
        date = getIntent().getStringExtra("Date");
        cleaningService = getIntent().getStringExtra("Service");
        month = getIntent().getStringExtra("Month");
        year = getIntent().getStringExtra("Year");

        tvAddress.setText("Address: " + address);
        tvDate.setText("Date: "+ date);
        tvService.setText("Cleaning Service: "+ cleaningService);

        btn_10_11=findViewById(R.id.btn_10_11);
        btn_11_12=findViewById(R.id. btn_11_12);
        btn_12_1=findViewById(R.id.btn_12_1);
        btn_1_2=findViewById(R.id.btn_1_2);
        btn_2_3=findViewById(R.id.btn_2_3);
        btn_3_4=findViewById(R.id.btn_3_4);
        btn_4_5=findViewById(R.id.btn_4_5);
        btn_5_6=findViewById(R.id.btn_5_6);

        btnBook = findViewById(R.id.btnBook);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                phoneNo = documentSnapshot.getString("PhoneNo");
                username = documentSnapshot.getString("Username");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SelectTimeSlot.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_10_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The counter we set early as 0
                //Calculate that the counter is 0 or 1
                count10AM = (count10AM+1) % 2;

                //If the counter is 1, the button will set to black color and text will set to white color .
                if (count10AM == 1){
                    btn_10_11.setBackgroundColor(Color.BLACK);
                    btn_10_11.setTextColor(Color.WHITE);
                    price+=10;  //the price will increase by 10
                    hour+=1;    //the hours will increase by 1
                }

                //else means 0, the button will set to white color and text will set to black color.
                else{
                    btn_10_11.setBackgroundColor(Color.WHITE);
                    btn_10_11.setTextColor(Color.BLACK);
                    price-=10; //the price will decrease by 10
                    hour-=1; //the hours will decrease by 1
                }

                //set the price text to the price value and call setBtnBookEnable function.
                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();
            }
        });

        btn_11_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count11AM = (count11AM+1) % 2;

                if (count11AM == 1){
                    btn_11_12.setBackgroundColor(Color.BLACK);
                    btn_11_12.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_11_12.setBackgroundColor(Color.WHITE);
                    btn_11_12.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }
                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_12_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count12PM = (count12PM+1) % 2;

                if (count12PM == 1){
                    btn_12_1.setBackgroundColor(Color.BLACK);
                    btn_12_1.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_12_1.setBackgroundColor(Color.WHITE);
                    btn_12_1.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1PM = (count1PM+1) % 2;

                if (count1PM == 1){
                    btn_1_2.setBackgroundColor(Color.BLACK);
                    btn_1_2.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_1_2.setBackgroundColor(Color.WHITE);
                    btn_1_2.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2PM = (count2PM+1) % 2;

                if (count2PM == 1){
                    btn_2_3.setBackgroundColor(Color.BLACK);
                    btn_2_3.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_2_3.setBackgroundColor(Color.WHITE);
                    btn_2_3.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count3PM = (count3PM+1) % 2;

                if (count3PM == 1){
                    btn_3_4.setBackgroundColor(Color.BLACK);
                    btn_3_4.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_3_4.setBackgroundColor(Color.WHITE);
                    btn_3_4.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_4_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count4PM = (count4PM+1) % 2;

                if (count4PM == 1){
                    btn_4_5.setBackgroundColor(Color.BLACK);
                    btn_4_5.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_4_5.setBackgroundColor(Color.WHITE);
                    btn_4_5.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btn_5_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count5PM = (count5PM+1) % 2;

                if (count5PM == 1){
                    btn_5_6.setBackgroundColor(Color.BLACK);
                    btn_5_6.setTextColor(Color.WHITE);
                    price+=10;
                    hour+=1;
                }

                else{
                    btn_5_6.setBackgroundColor(Color.WHITE);
                    btn_5_6.setTextColor(Color.BLACK);
                    price-=10;
                    hour-=1;
                }

                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference history = db.collection("Users").document(userID);
                history.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String history_number = documentSnapshot.getString("History");
                        historyNum= Integer.parseInt(history_number);

                        //increase the history by 1 in the Users collection
                        historyNum++;
                        addHistoryNum(historyNum + "");

                    }
                });

                ArrayList<String> getTime = new ArrayList<>();
                //the string time will store in  getTime arrayList
                if (count10AM == 1){
                    getTime.add("10AM - 11AM");
                }
                if (count11AM == 1){
                    getTime.add("11AM - 12PM");
                }

                if (count12PM == 1){
                    getTime.add("12PM - 1 PM");
                }

                if (count1PM == 1){
                    getTime.add("1 PM - 2 PM");
                }

                if (count2PM == 1){
                    getTime.add("2 PM - 3 PM");
                }

                if (count3PM == 1){
                    getTime.add("3 PM - 4 PM");
                }

                if (count4PM == 1){
                    getTime.add("4 PM - 5 PM");
                }

                if (count5PM == 1){
                    getTime.add("5 PM - 6 PM");
                }


                DocumentReference storeHistoryAdmin = db.collection("User Booking").document();
                Map<String, Object> historyUser = new HashMap<>();
                historyUser.put("Cleaning Service", cleaningService);
                historyUser.put("Username", username);
                historyUser.put("Date", date);
                historyUser.put("Address", address);
                historyUser.put("PhoneNo", phoneNo);
                historyUser.put("Price", price + "");
                historyUser.put("Hour", hour + "");
                historyUser.put("Status", "unpaid");
                historyUser.put("Progress", "pending");
                historyUser.put("UserID", userID);
                historyUser.put("Month", month);
                historyUser.put("Year", year);

                //Get the time from the array list
                for (int i=0; i<getTime.size(); i++){
                    int count = i;

                    //store the time into the database
                    historyUser.put("Time " + ++count, getTime.get(i));

                }
                storeHistoryAdmin.set(historyUser);

                //reset the arraylist
                getTime.clear();

                hour  = 0;

                //set the text price into the price value and call setBtnBookEnable function
                tvPrice.setText("RM " + price + ".00" );
                setBtnBookEnable();

                Toast.makeText(SelectTimeSlot.this, "Book Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),UserCart.class));
            }
        });
    }

    public void setBtnBookEnable(){
        //if the price equals to 0 and the button will set to unable.
        if (price == 0){
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(Color.parseColor("#D3D3D3"));
            btnBook.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
        //if the price is not equals to 0 and the button will set to enable.
        else{
            btnBook.setEnabled(true);
            btnBook.setBackgroundColor(Color.parseColor("#0099ff"));
            btnBook.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    private void addHistoryNum(String historyCount){
        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.update("History", historyCount);
    }

}