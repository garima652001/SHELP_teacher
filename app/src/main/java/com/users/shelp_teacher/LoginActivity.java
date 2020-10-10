package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Login;
import com.users.shelp_teacher.Response.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    String emailtxt, passwordtxt ;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.button);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPass);
        progressbar=findViewById(R.id.progbarlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                LoginUser();
            }
        });
        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, Resetotpsend.class);
                finish();
                startActivity(intent);

            }
        });
    }

    private void LoginUser() {
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        if (emailtxt.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
            email.setError("Please enter a valid email id");
            email.requestFocus();
            return;
        }
        if (passwordtxt.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (passwordtxt.length() < 5) {
            password.setError("Password should be atleast 5 char long");
            password.requestFocus();
            return;
        } else {
            RetroWork();
        }
    }

    private void RetroWork() {
        Login log1 = new Login(emailtxt, passwordtxt);
        Call<LoginResponse> call = Retroclient.getInstance().getapi().loginUser(log1);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    progressbar.setVisibility(View.GONE);
                    try {
                        String string = response.errorBody().string();
                        JSONObject jsonObject1 = new JSONObject(string);
                        String wrong = jsonObject1.getString("message");
                        Toast.makeText(getApplicationContext(), wrong, Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject(string);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject meals = jsonArray.getJSONObject(0);
                        String wrong1=meals.getString("msg");
                        Toast.makeText(getApplicationContext(), wrong1, Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), response.body().getMessage() , Toast.LENGTH_LONG).show();
                    String token = response.body().getToken();
                    Sharedprefs.saveSharedsetting(LoginActivity.this,"Clip" ,"false");
                    Sharedprefs.sharedprefsave(getApplicationContext(), response.body().getUsername(),token, response.body().getUserId(),emailtxt);
                    Intent islogged = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(islogged);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
