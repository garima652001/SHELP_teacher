package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.users.shelp_teacher.Fragments.Createfragment;
import com.users.shelp_teacher.Fragments.Homefragment;
import com.users.shelp_teacher.Fragments.Profilefragment;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   checkuser();

        chipNavigationBar = findViewById(R.id.bottom_nav);
        chipNavigationBar.setItemSelected(R.id.nav_home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Homefragment()).commit();
        menu();
    }

    private void menu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch(i){
                    case R.id.nav_home:
                        fragment= new Homefragment();
                        break;

                    case R.id.nav_profile:
                        fragment= new Profilefragment();
                        break;

                    case R.id.nav_create:
                        fragment= new Createfragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
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