package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Resetotp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resetotpsend extends AppCompatActivity {

    EditText tv_emailadd;
    String email;
    String token;
    private String res = "";
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetotpsend);
        tv_emailadd = findViewById(R.id.etemailreset);
        progress = findViewById(R.id.prog_email);
        findViewById(R.id.tv_login2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resetotpsend.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_requestotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = tv_emailadd.getText().toString();
                if (email.isEmpty()) {
                    tv_emailadd.setError("Email is required");
                    tv_emailadd.requestFocus();
                    return;
                } else {
                    progress.setVisibility(View.VISIBLE);
                    sent();


                }
            }
        });
    }

    private void sent() {
       Resetotp reset = new Resetotp(email);

        Call<ResponseBody> call1 = Retroclient
                .getInstance()
                .getapi()
                .sendotp(reset);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                try {
                    if (!response.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        //Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        assert response.errorBody() != null;
                        String s = response.errorBody().string();
                        JSONObject jsonObject1 = new JSONObject(s);
                        String msg = jsonObject1.getString("msg");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                    } else {
                        progress.setVisibility(View.GONE);
                        assert response.body() != null;
                        String s = response.body().string();
                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject obj = jsonObject.getJSONObject("result");
                        token = obj.getString("token");
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Resetotpsend.this, Resetotpcheck.class);
                        intent.putExtra("token", token);
                        intent.putExtra("email", email);
                        finish();
                        startActivity(intent);

                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
        }

