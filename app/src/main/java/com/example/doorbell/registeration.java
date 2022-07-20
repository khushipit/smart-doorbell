package com.example.doorbell;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class registeration extends AppCompatActivity {

    String uname, email, phone, pwd, addr;
    EditText nm, mail, phn, pass, add;

    boolean response_error;
    String response_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.scale);
        findViewById(R.id.textView).startAnimation(a);

        nm = (EditText) findViewById(R.id.editTextTextPersonName);
        mail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        phn = (EditText) findViewById(R.id.editTextPhone);
        add = (EditText) findViewById(R.id.editTextTextPostalAddress);


        findViewById(R.id.textView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registeration.this, Login_page.class));
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        uname = nm.getText().toString().trim();
        email = mail.getText().toString().trim();
        phone = phn.getText().toString().trim();
        pwd = pass.getText().toString().trim();
        addr = add.getText().toString().trim();

        if (TextUtils.isEmpty(uname)) {
            nm.setError("Please enter username");
            nm.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mail.setError("Please enter your email");
            mail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email");
            mail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            pass.setError("Enter a password");
            pass.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            phn.setError("Enter phone number");
            phn.requestFocus();
            return;
        }
        if(phone.length()<10){
            phn.setError("Enter a valid phone number");
            phn.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(addr)) {
            add.setError("Enter a Address");
            add.requestFocus();
            return;
        }

        new Thread(new Runnable() {
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", uname)
                        .addFormDataPart("email_id", email)
                        .addFormDataPart("password", pwd)
                        .addFormDataPart("phone_no", phone)
                        .addFormDataPart("address", addr)
                        .build();

                Request request = new Request.Builder()
                        .url(URLs.URL_REGISTER)
                        .post(requestBody)
                        .build();

                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = null;
                              try {

                    jsonObject = new JSONObject(response.body().string());
                    Log.e("asd", String.valueOf(jsonObject));
                    response_error = jsonObject.getBoolean("error");
                    response_msg = jsonObject.getString("message");

                    registeration.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!response_error) {
                                Toast.makeText(getApplicationContext(), response_msg, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registeration.this,Login_page.class));
                            } else {
                                Toast.makeText(getApplicationContext(), response_msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

