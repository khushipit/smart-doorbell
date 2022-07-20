package com.example.doorbell;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Homepage extends AppCompatActivity {
    TextView temp, light, locked, unlocked, statuss;
    User user;
    String uid, off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        user = SharedPrefManager.getInstance(this).getUser();
        uid = String.valueOf(user.getLogin_id());

        findViewById(R.id.textView7).startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        statuss = findViewById(R.id.status);

        findViewById(R.id.temp).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
        findViewById(R.id.light).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translatelight));
        findViewById(R.id.lock).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
        findViewById(R.id.unlock).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translatelight));
        viewdata();
        BottomNavigationView btm = findViewById(R.id.bottmnavi);
        btm.setSelectedItemId(R.id.home);

        doorstatus();

        findViewById(R.id.temp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, temprature.class));
            }
        });

        findViewById(R.id.light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, light_ldr.class));
            }
        });
        findViewById(R.id.lock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dunlock();
            }
        });
        findViewById(R.id.unlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlock();
            }
        });

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.security:
                        startActivity(new Intent(getApplicationContext(), security.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), accountme.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }

    private void doorstatus() {
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

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");

                        String ttts = obj.getString("door");



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
                params.put("door", uid);
                Log.e("pass", uid);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETDATA, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    public void viewdata() {

        temp = findViewById(R.id.txt_temp_value);
        light = findViewById(R.id.txt_light_value);
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

                        String ttt = obj.getString("temp");
                        String lll = obj.getString("ldr");

                        temp.setText("Value: " + ttt);
                        light.setText("Value: " + lll);

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

    private void dunlock() {

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        /*Intent Page = new Intent(Activity_Light.this, HomeActivity.class);
                        startActivity(Page);*/
                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

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
                params.put("status", "OFF");
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DOOR_OPEN_CLOSE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    private void dlock() {

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        /*Intent Page = new Intent(Activity_Light.this, HomeActivity.class);
                        startActivity(Page);*/
                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

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
                params.put("status", "ON");
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DOOR_OPEN_CLOSE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

}