package com.example.doorbell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.spin);
        findViewById(R.id.imageView2).startAnimation(a);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));

                } else{
                    Intent i = new Intent(MainActivity.this, onboarding.class);
                    startActivity(i);
                    // close this activity
                    finish();

                }
            }
        },2000);


    }
}