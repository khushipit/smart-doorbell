package com.example.doorbell;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class accountme extends AppCompatActivity {

    ListView listView;
    String[] names = {"Edit Profile", "Sign out"};
    int[] images = {R.drawable.ic_baseline_person_outline_24 ,R.drawable.ic_baseline_exit_to_app_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountme);

        findViewById(R.id.imageView3).startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        findViewById(R.id.textView7).startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        listView = findViewById(R.id.list_view);

        adapter ad = new adapter(accountme.this, names, images);
        listView.setAdapter(ad);

        BottomNavigationView btm = findViewById(R.id.bottmnavi);
        btm.setSelectedItemId(R.id.account);

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.security:
                        startActivity(new Intent(getApplicationContext(), security.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.account:
                        return true;

                }
                return false;
            }
        });

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login_page.class));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(accountme.this, editprofile.class));
                }  else if (position == 1) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    finish();
                }
            }
        });

    }
}