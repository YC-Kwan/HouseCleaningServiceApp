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

    private String address, date, cleaningService, userID, phoneNo,username,month;

    private String time_10 = "", time_11 = "", time_12 = "",
            time_1 = "", time_2 = "", time_3= "",
            time_4 = "", time_5 = "";
    private int price = 0, hour = 0, book_month,historyNum;

    private int count10AM = 0, count11AM = 0, count12PM = 0, count1PM = 0, count2PM = 0, count3PM = 0, count4PM = 0,
            count5PM = 0;

    private  int monthCount1,monthCount2,monthCount3,monthCount4,monthCount5,monthCount6,monthCount7,monthCount8,monthCount9,
            monthCount10,monthCount11,monthCount12,totalOrder;

    private String monthCounter1,monthCounter2,monthCounter3,monthCounter4,monthCounter5,monthCounter6,monthCounter7,monthCounter8,monthCounter9,
            monthCounter10,monthCounter11,monthCounter12,totalOrders;

    private  int monthEarn1,monthEarn2,monthEarn3,monthEarn4,monthEarn5,monthEarn6,monthEarn7,monthEarn8,monthEarn9,
            monthEarn10,monthEarn11,monthEarn12,totalEarn;

    private String monthEarns1,monthEarns2,monthEarns3,monthEarns4,monthEarns5,monthEarns6,monthEarns7,monthEarns8,monthEarns9,
            monthEarns10,monthEarns11,monthEarns12,totalEarns;

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

        book_month = Integer.parseInt(month);

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

                DocumentReference monthCounter = db.collection("Order").document("8aZa5asmGgQX4UN7yDJ9");
                monthCounter.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        monthCounter1= documentSnapshot.getString("month1");
                        monthCounter2= documentSnapshot.getString("month2");
                        monthCounter3= documentSnapshot.getString("month3");
                        monthCounter4= documentSnapshot.getString("month4");
                        monthCounter5= documentSnapshot.getString("month5");
                        monthCounter6= documentSnapshot.getString("month6");
                        monthCounter7= documentSnapshot.getString("month7");
                        monthCounter8= documentSnapshot.getString("month8");
                        monthCounter9= documentSnapshot.getString("month9");
                        monthCounter10= documentSnapshot.getString("month10");
                        monthCounter11= documentSnapshot.getString("month11");
                        monthCounter12= documentSnapshot.getString("month12");
                        totalOrders= documentSnapshot.getString("totalorder");

                        monthCount1 = Integer.parseInt(monthCounter1);
                        monthCount2 = Integer.parseInt(monthCounter2);
                        monthCount3 = Integer.parseInt(monthCounter3);
                        monthCount4 = Integer.parseInt(monthCounter4);
                        monthCount5 = Integer.parseInt(monthCounter5);
                        monthCount6 = Integer.parseInt(monthCounter6);
                        monthCount7 = Integer.parseInt(monthCounter7);
                        monthCount8 = Integer.parseInt(monthCounter8);
                        monthCount9 = Integer.parseInt(monthCounter9);
                        monthCount10 = Integer.parseInt(monthCounter10);
                        monthCount11 = Integer.parseInt(monthCounter11);
                        monthCount12 = Integer.parseInt(monthCounter12);
                        totalOrder = Integer.parseInt(totalOrders);


                        if(book_month == 1){
                            monthCount1++;
                        }else if(book_month == 2){
                            monthCount2++;
                        }else if(book_month == 3){
                            monthCount3++;
                        }else if(book_month == 4){
                            monthCount4++;
                        }else if(book_month == 5){
                            monthCount5++;
                        }else if(book_month == 6){
                            monthCount6++;
                        }else if(book_month == 7){
                            monthCount7++;
                        }else if(book_month == 8){
                            monthCount8++;
                        }else if(book_month == 9){
                            monthCount9++;
                        }else if(book_month == 10){
                            monthCount10++;
                        }else if(book_month == 11){
                            monthCount11++;
                        }else if(book_month == 12) {
                            monthCount12++;
                        }
                        totalOrder = monthCount1 + monthCount2 + monthCount3 + monthCount4
                                + monthCount5 +monthCount6 + monthCount7+monthCount8 +monthCount9
                                + monthCount10 + monthCount11 + monthCount12;

                        monthCounter.update("totalorder",totalOrder + "");

                        monthCounter.update("month1", monthCount1 + "");
                        monthCounter.update("month2", monthCount2 + "");
                        monthCounter.update("month3", monthCount3 + "");
                        monthCounter.update("month4", monthCount4 + "");
                        monthCounter.update("month5", monthCount5 + "");
                        monthCounter.update("month6", monthCount6 + "");
                        monthCounter.update("month7", monthCount7 + "");
                        monthCounter.update("month8", monthCount8 + "");
                        monthCounter.update("month9", monthCount9 + "");
                        monthCounter.update("month10", monthCount10 + "");
                        monthCounter.update("month11", monthCount11 + "");
                        monthCounter.update("month12", monthCount12 + "");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectTimeSlot.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference monthEarn= db.collection("Order").document("uv5uyCIl0ZGfytvqnNv2");
                monthEarn.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        monthEarns1= documentSnapshot.getString("m1");
                        monthEarns2= documentSnapshot.getString("m2");
                        monthEarns3= documentSnapshot.getString("m3");
                        monthEarns4= documentSnapshot.getString("m4");
                        monthEarns5= documentSnapshot.getString("m5");
                        monthEarns6= documentSnapshot.getString("m6");
                        monthEarns7= documentSnapshot.getString("m7");
                        monthEarns8= documentSnapshot.getString("m8");
                        monthEarns9= documentSnapshot.getString("m9");
                        monthEarns10= documentSnapshot.getString("m10");
                        monthEarns11= documentSnapshot.getString("m11");
                        monthEarns12= documentSnapshot.getString("m12");
                        totalEarns= documentSnapshot.getString("totalearn");

                        monthEarn1 = Integer.parseInt(monthEarns1);
                        monthEarn2 = Integer.parseInt(monthEarns2);
                        monthEarn3 = Integer.parseInt(monthEarns3);
                        monthEarn4 = Integer.parseInt(monthEarns4);
                        monthEarn5 = Integer.parseInt(monthEarns5);
                        monthEarn6 = Integer.parseInt(monthEarns6);
                        monthEarn7 = Integer.parseInt(monthEarns7);
                        monthEarn8 = Integer.parseInt(monthEarns8);
                        monthEarn9 = Integer.parseInt(monthEarns9);
                        monthEarn10 = Integer.parseInt(monthEarns10);
                        monthEarn11 = Integer.parseInt(monthEarns11);
                        monthEarn12 = Integer.parseInt(monthEarns12);
                        totalEarn = Integer.parseInt(totalEarns);


                        if(book_month == 1){
                            monthEarn1+=price;
                        }else if(book_month == 2){
                            monthEarn2+=price;
                        }else if(book_month == 3){
                            monthEarn3+=price;
                        }else if(book_month == 4){
                            monthEarn4+=price;
                        }else if(book_month == 5){
                            monthEarn5+=price;
                        }else if(book_month == 6){
                            monthEarn6+=price;
                        }else if(book_month == 7){
                            monthEarn7+=price;
                        }else if(book_month == 8){
                            monthEarn8+=price;
                        }else if(book_month == 9){
                            monthEarn9+=price;
                        }else if(book_month == 10){
                            monthEarn10+=price;
                        }else if(book_month == 11){
                            monthEarn11+=price;
                        }else if(book_month == 12) {
                            monthEarn12+=price;
                        }

                        totalEarn = monthEarn1 + monthEarn2 + monthEarn3 + monthEarn4
                                + monthEarn5 +monthEarn6 + monthEarn7 + monthEarn8 +monthEarn9
                                + monthEarn10 + monthEarn11 + monthEarn12;

                        monthEarn.update("totalearn",totalEarn + "");

                        monthEarn.update("m1", monthEarn1 + "");
                        monthEarn.update("m2", monthEarn2 + "");
                        monthEarn.update("m3", monthEarn3 + "");
                        monthEarn.update("m4", monthEarn4 + "");
                        monthEarn.update("m5", monthEarn5 + "");
                        monthEarn.update("m6", monthEarn6 + "");
                        monthEarn.update("m7", monthEarn7 + "");
                        monthEarn.update("m8", monthEarn8 + "");
                        monthEarn.update("m9", monthEarn9 + "");
                        monthEarn.update("m10", monthEarn10 + "");
                        monthEarn.update("m11", monthEarn11 + "");
                        monthEarn.update("m12", monthEarn12 + "");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectTimeSlot.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

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

                //Store the time booking information into user history
                DocumentReference storeHistory = db.collection("Users").document(userID)
                        .collection("History").document();
                Map<String, Object> historyData = new HashMap<>();
                historyData.put("Cleaning Service", cleaningService);
                historyData.put("Date", date);
                historyData.put("Address", address);
                historyData.put("PhoneNo", phoneNo);
                historyData.put("Price", price + "");
                historyData.put("Hour", hour + "");

                //Get the time from the array list
                for (int i=0; i<getTime.size(); i++){
                    int count = i;

                    //store the time into the database
                    historyData.put("Time " + ++count, getTime.get(i));

                }
                storeHistory.set(historyData);

                DocumentReference storeHistoryAdmin = db.collection("User Booking").document();
                Map<String, Object> historyUser = new HashMap<>();
                historyUser.put("Cleaning Service", cleaningService);
                historyUser.put("Username", username);
                historyUser.put("Date", date);
                historyUser.put("Address", address);
                historyUser.put("PhoneNo", phoneNo);
                historyUser.put("Price", price + "");
                historyUser.put("Hour", hour + "");

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
                startActivity(new Intent(getApplicationContext(),User.class));
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