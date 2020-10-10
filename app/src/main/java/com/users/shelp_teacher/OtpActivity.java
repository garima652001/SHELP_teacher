package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Resendotp;
import com.users.shelp_teacher.Request.Verify;
import com.users.shelp_teacher.Response.OtpResponse;
import com.users.shelp_teacher.Response.ResendOtpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_otp;
    String token,email;
    TextView resend;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent= getIntent();
        resend=findViewById(R.id.tvresend_otp);
        token = intent.getStringExtra("Token");
        email=intent.getStringExtra("email");
        et_otp= findViewById(R.id.etotp);
        progressbar= findViewById(R.id.progbarotp);

        findViewById(R.id.btn_confirmemail).setOnClickListener(this);
        findViewById(R.id.tv_login1).setOnClickListener(this);
        findViewById(R.id.tvresend_otp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_confirmemail:
                progressbar.setVisibility(View.VISIBLE);
                verify();
                break;

            case R.id.tv_login1:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.tvresend_otp:
                resend.setEnabled(false);

                resend.postDelayed(new Runnable() {
                    public void run() {
                        resend.setEnabled(true);
                    }
                }, 3*60*100);
                resend();
                break;
        }
    }

    private void resend() {
        Resendotp reotp=new Resendotp(token,email);
        Call<ResendOtpResponse> call1=Retroclient
                .getInstance()
                .getapi()
                .resend(reotp);

        call1.enqueue(new Callback<ResendOtpResponse>() {
            @Override
            public void onResponse(Call<ResendOtpResponse> call, Response<ResendOtpResponse> response) {
                assert response.body() != null;
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "New OTP has been sent to your mail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResendOtpResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
            });
        }

    private void verify() {
        String otp=et_otp.getText().toString();
        if(otp.isEmpty())
        {
            et_otp.setError("OTP is required");
            et_otp.requestFocus();
            return;
        }
        else
        {
            Verify verify= new Verify(token,otp);
            Call<OtpResponse> call= Retroclient
                    .getInstance()
                    .getapi()
                    .verifymail(verify);
            call.enqueue(new Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                    try {
                        if (response.code() == 200) {
                            progressbar.setVisibility(View.GONE);
                            OtpResponse res = response.body();
                            Toast.makeText(OtpActivity.this, res.getMessage(), Toast.LENGTH_LONG).show();
                            Sharedprefs.saveSharedsetting(OtpActivity.this,"Clip" ,"false");
                            Sharedprefs.sharedprefsave(getApplicationContext(), res.getUsername(),res.getToken(),res.getUserId(),email);
                            Intent isverified = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(isverified);
                            finish();
                        } else {
                            progressbar.setVisibility(View.GONE);
                            String s = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(s);
                            String message = jsonObject.getString("message");
                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });

        }
    }
}