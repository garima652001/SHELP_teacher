package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkuser();
    }
    public void checkuser()
    {
        Boolean check= Boolean.valueOf(Sharedprefs.readShared(MainActivity.this,"Clip","true"));

        Intent intro = new Intent(MainActivity.this, SignupActivity.class);
        intro.putExtra("Clip",check);

        if(check){
            startActivity(intro);
        }
    }
}