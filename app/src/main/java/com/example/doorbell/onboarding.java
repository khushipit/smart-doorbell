package com.example.doorbell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;

import com.example.doorbell.R.drawable;

import io.github.dreierf.materialintroscreen.MaterialIntroActivity;
import io.github.dreierf.materialintroscreen.SlideFragmentBuilder;

public class onboarding extends MaterialIntroActivity {

    String prevStarted = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                .title("Smart Lock")
                .description("Open and close your door using smart phone with a single tap")
                .image(R.drawable.lock)
                .buttonsColor(R.color.black)
                .backgroundColor(R.color.ex)
                .build()  );


        addSlide(new SlideFragmentBuilder()
                .title("Wireless Security")
                .description("Alert message when smoke and fire detected")
                .image(R.drawable.security)
                .buttonsColor(R.color.black)
                .backgroundColor(R.color.ex)
                .build()  );

        addSlide(new SlideFragmentBuilder()
                .title("Get Notified")
                .description("Instant notification when someone near the door")
                .image(R.drawable.security)
                .buttonsColor(R.color.black)
                .backgroundColor(R.color.ex)
                .build()  );
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            onFinish();
        }
    }
    @Override
    public void onFinish() {
        super.onFinish();
        Intent i =new Intent(onboarding.this,Login_page.class);
        startActivity(i);
    }
}