package com.example.doorbell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forgotpass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.scale);
        findViewById(R.id.textView2).startAnimation(a);

        Button b =(Button) findViewById(R.id.button3);
        EditText pass1=(EditText) findViewById(R.id.editTextTextPassword4) ;
        EditText cpass=(EditText) findViewById(R.id.editTextTextPassword3) ;

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1=pass1.getText().toString();
                String p2=cpass.getText().toString();

                if (p1.equals(p2)) {
                    Toast.makeText(getApplicationContext(), "Password is set", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(forgotpass.this,Login_page.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), " both password must be same", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}