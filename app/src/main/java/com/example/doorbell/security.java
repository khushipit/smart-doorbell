package com.example.doorbell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class security extends AppCompatActivity {
    TextView txt_fire,txt_smoke,txt_motion;
    String uid;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        txt_fire=findViewById(R.id.txt_fire_value);
        txt_smoke=findViewById(R.id.txt_smoke_value);
        txt_motion=findViewById(R.id.txt_motion_value);

        user = SharedPrefManager.getInstance(this).getUser();
        uid = String.valueOf(user.getLogin_id());
        Animation a= AnimationUtils.loadAnimation(this,R.anim.scale);
        findViewById(R.id.textView7).startAnimation(a);
        viewdata();
        findViewById(R.id.fire).startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
        findViewById(R.id.smoke).startAnimation(AnimationUtils.loadAnimation(this,R.anim.translatelight));
        findViewById(R.id.motion).startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));

        BottomNavigationView btm=findViewById(R.id.bottmnavi);
        btm.setSelectedItemId(R.id.security);

        findViewById(R.id.fire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,fire.class));
            }
        });

        findViewById(R.id.smoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,smoke.class));
            }
        });

        findViewById(R.id.motion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(security.this,motion.class));
            }
        });

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.security:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Homepage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),accountme.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }


    public void viewdata() {

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");

                        String fff=obj.getString("flame");
                        String mmm=obj.getString("pir");
                        String sss=obj.getString("smoke");

                        txt_fire.setText("Value: "+fff);
                        txt_motion.setText("Value: "+mmm);
                        txt_smoke.setText("Value: "+sss);

                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("temp", uid);
                Log.e("pass", uid);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETDATA, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }
}