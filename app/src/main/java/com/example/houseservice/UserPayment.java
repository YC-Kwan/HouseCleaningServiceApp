package com.example.houseservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserPayment extends AppCompatActivity {

    private String cartID, year, month, bookPrice;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    private  int monthCount1,monthCount2,monthCount3,monthCount4,monthCount5,monthCount6,monthCount7,monthCount8,monthCount9,
            monthCount10,monthCount11,monthCount12,totalOrder, book_month, price;

    private String monthCounter1,monthCounter2,monthCounter3,monthCounter4,monthCounter5,monthCounter6,monthCounter7,monthCounter8,monthCounter9,
            monthCounter10,monthCounter11,monthCounter12,totalOrders;

    private  int monthEarn1,monthEarn2,monthEarn3,monthEarn4,monthEarn5,monthEarn6,monthEarn7,monthEarn8,monthEarn9,
            monthEarn10,monthEarn11,monthEarn12,totalEarn;

    private String monthEarns1,monthEarns2,monthEarns3,monthEarns4,monthEarns5,monthEarns6,monthEarns7,monthEarns8,monthEarns9,
            monthEarns10,monthEarns11,monthEarns12,totalEarns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);

        setContentView(R.layout.activity_user_payment);

        com.craftman.cardform.CardForm cardForm = (com.craftman.cardform.CardForm)
                findViewById(R.id.card_form);

        Button btnPay = findViewById(R.id.btn_pay);
        TextView txtPrice = (TextView)findViewById(R.id.payment_amount);
        txtPrice.setVisibility(View.INVISIBLE);

        cartID = getIntent().getStringExtra("cartID");

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnPay.setText("Pay Now");

        DocumentReference updatePayment = db.collection("User Booking").document(cartID);
        updatePayment.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                month= documentSnapshot.getString("Month");
                book_month = Integer.parseInt(month);
                year= documentSnapshot.getString("Year");
                bookPrice= documentSnapshot.getString("Price");
                price = Integer.parseInt(bookPrice);
            }
        });

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                startActivity(new Intent(getApplicationContext(),User.class));
                updatePayment();
            }
        });
    }

    public void updatePayment(){
        DocumentReference updatePayment = db.collection("User Booking").document(cartID);
        updatePayment.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                updatePayment.update("Status", "paid");
                getAnalytic();
                Toast.makeText(UserPayment.this, "Pay Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserPayment.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAnalytic(){

        DocumentReference monthCounter = db.collection("Order").document(year).collection("Analytic").document("Total Order");
        monthCounter.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                monthCounter1= documentSnapshot.getString("m1");
                monthCounter2= documentSnapshot.getString("m2");
                monthCounter3= documentSnapshot.getString("m3");
                monthCounter4= documentSnapshot.getString("m4");
                monthCounter5= documentSnapshot.getString("m5");
                monthCounter6= documentSnapshot.getString("m6");
                monthCounter7= documentSnapshot.getString("m7");
                monthCounter8= documentSnapshot.getString("m8");
                monthCounter9= documentSnapshot.getString("m9");
                monthCounter10= documentSnapshot.getString("m10");
                monthCounter11= documentSnapshot.getString("m11");
                monthCounter12= documentSnapshot.getString("m12");
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

                monthCounter.update("m1", monthCount1 + "");
                monthCounter.update("m2", monthCount2 + "");
                monthCounter.update("m3", monthCount3 + "");
                monthCounter.update("m4", monthCount4 + "");
                monthCounter.update("m5", monthCount5 + "");
                monthCounter.update("m6", monthCount6 + "");
                monthCounter.update("m7", monthCount7 + "");
                monthCounter.update("m8", monthCount8 + "");
                monthCounter.update("m9", monthCount9 + "");
                monthCounter.update("m10", monthCount10 + "");
                monthCounter.update("m11", monthCount11 + "");
                monthCounter.update("m12", monthCount12 + "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserPayment.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference monthEarn= db.collection("Order").document(year).collection("Analytic").document("Total Earn");
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
                Toast.makeText(UserPayment.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}