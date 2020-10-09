package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
             checkuser();
           }
       },2500);
    }

    public void checkuser() {
            Boolean check= Boolean.valueOf(Sharedprefs.readShared(SplashActivity.this,"Clip","true"));

            Intent intro = new Intent(SplashActivity.this, SignupActivity.class);
            intro.putExtra("Clip",check);

            if(check){
                finish();
                startActivity(intro);
            }
            else{
                Intent mainintent =new Intent(SplashActivity.this, MainActivity.class);
                finish();
                startActivity(mainintent);
            }

        }
    }
