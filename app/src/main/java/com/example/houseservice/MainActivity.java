package com.example.houseservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=2500;
    Animation topanim,btmanim;
    ImageView img;
    TextView title,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btmanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img=findViewById(R.id.imageView);
        title=findViewById(R.id.textView);
        slogan=findViewById(R.id.textView2);

        img.setAnimation(topanim);
        title.setAnimation(btmanim);
        slogan.setAnimation(btmanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        },SPLASH_SCREEN);


    }
}