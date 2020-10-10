package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Checkotp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resetotpcheck extends AppCompatActivity {

    String token;
    EditText et_otpreset;
    String email;
    String otp;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetotpcheck);
        et_otpreset = findViewById(R.id.etotpreset);
        progress= findViewById(R.id.progreset);
        token = getIntent().getStringExtra("token");
        email = getIntent().getStringExtra("email");
        findViewById(R.id.btn_requestotpcheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = et_otpreset.getText().toString();
                if (otp.isEmpty()) {
                    et_otpreset.setError("Email cannot be empty");
                    et_otpreset.requestFocus();
                } else {
                    progress.setVisibility(View.VISIBLE);
                    sent();
                }
            }
        });
    }

    private void sent() {
        Checkotp checkotp = new Checkotp(token, otp);
        Call<ResponseBody> call= Retroclient
                .getInstance()
                .getapi()
                .checkotp(checkotp);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    try {
                        assert response.errorBody() != null;
                        String s = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(s);
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    progress.setVisibility(View.GONE);
                }
                else {
                    try {
                        assert response.body() != null;
                        String str = response.body().string();
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Resetotpcheck.this, Resetpassword.class);
                            intent.putExtra("email", email);
                            finish();
                            startActivity(intent);
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

}