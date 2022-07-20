package com.example.doorbell;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class editprofile extends AppCompatActivity {

    EditText nm,pwd,add,phno,mail;
    Button UpdateProfile;
    String uid, role, status, dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.scale);
        findViewById(R.id.textView7).startAnimation(a);

        nm=(EditText) findViewById(R.id.Name);
        pwd=(EditText) findViewById(R.id.Password);
        add=(EditText) findViewById(R.id.Address);
        phno=(EditText) findViewById(R.id.Phone);
        mail=(EditText) findViewById(R.id.Email);
        UpdateProfile=(Button) findViewById(R.id.btn_update_Profile);

        User user = SharedPrefManager.getInstance(this).getUser();
        uid = user.getLogin_id();
        role = user.getRole();
        status = user.getStatus();
        dp = user.getDP();

        nm.setText(user.getName());
        pwd.setText(user.getPassword());
        add.setText(user.getAddress());
        phno.setText(user.getPhone_no());
        mail.setText(user.getEmail_id());

        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateprofile();

            }
        });
    }


    public void updateprofile() {

        final ProgressDialog progressDialog = new ProgressDialog(editprofile.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Updating..");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess()
    {
        String u_username = nm.getText().toString();
        String u_email = mail.getText().toString();
        String u_phoneNo = phno.getText().toString();
        String u_password = pwd.getText().toString();
        String u_address = add.getText().toString();

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
                       //creating a new user object
                        User userupdate = new User(
                                uid,
                                u_username,
                                u_email,
                                u_password,
                                u_phoneNo,
                                u_address,
                                role,
                                status,
                                dp
                        );
                        SharedPrefManager.getInstance(getApplicationContext()).update(userupdate);

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent Page = new Intent(editprofile.this, Homepage.class);
                        startActivity(Page);
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
                params.put("name", u_username);
                params.put("email_id", u_email);
                params.put("password", u_password);
                params.put("phone_no", u_phoneNo);
                params.put("address", u_address);
                params.put("login_id", uid);

                Log.e("pass",u_password);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_UPDATEPROFILE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}