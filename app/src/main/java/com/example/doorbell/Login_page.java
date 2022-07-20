package com.example.doorbell;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login_page extends AppCompatActivity {

    EditText ph, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ph = (EditText) findViewById(R.id.editTextPhone2);
        pass = (EditText) findViewById(R.id.editTextTextPassword2);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        findViewById(R.id.textView7).startAnimation(a);

        findViewById(R.id.textView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login_page.this, forgotpass.class));
            }
        });


        findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login_page.this, registeration.class));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {

        final String phoneno = ph.getText().toString();
        final String password = pass.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(phoneno)) {
            ph.setError("Please enter your phoneno");
            ph.requestFocus();
            return;
        }
        if (phoneno.length() < 10) {
            ph.setError("Enter a valid phone number");
            ph.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            pass.setError("Please enter your password");
            pass.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getString("login_id"),
                                userJson.getString("name"),
                                userJson.getString("email_id"),
                                userJson.getString("password"),
                                userJson.getString("phone_no"),
                                userJson.getString("address"),
                                userJson.getString("role"),
                                userJson.getString("status"),
                                userJson.getString("DP")

                        );

                        Log.e("phoneno", userJson.getString("phone_no"));//phone_no


                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        finish();
                        Intent i = new Intent(Login_page.this, Homepage.class);
                        startActivity(i);

                    } else {

                        //Toast.makeText(getApplicationContext(), String.valueOf(obj.getBoolean("error")), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                params.put("phone_no", phoneno);
                params.put("password", password);

                Log.e("pass", password);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    //To prevent user to going to previous activity
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}