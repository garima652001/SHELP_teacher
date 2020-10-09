package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.R;
import com.users.shelp_teacher.Request.Signup;
import com.users.shelp_teacher.Response.SignupResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressbar;
    private EditText et_email, et_password, et_name, et_confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_name = findViewById(R.id.etname);
        et_email = findViewById(R.id.etemail);
        et_password = findViewById(R.id.etpassword);
        et_confirmpassword = findViewById(R.id.etconfirmpassword);
        progressbar=findViewById(R.id.progbar);

        findViewById(R.id.btnsignup).setOnClickListener(this);
        findViewById(R.id.tvlogin).setOnClickListener(this);

    }


    private void signup() {
        String name = et_name.getText().toString();
        final String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirmpassword = et_confirmpassword.getText().toString();

        if (name.isEmpty()) {
            et_name.setError("Name cannot be empty");
            et_name.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }
        if (password.length() < 5) {
            et_password.setError("Password must be atleast 5 characters long");
            et_password.requestFocus();
            return;
        }

        if (!confirmpassword.equals(password)) {
            et_confirmpassword.setError("Passwords do not match");
            et_confirmpassword.requestFocus();
            return;
        } else {
            Signup signin = new Signup(name, email, password);
            Call<SignupResponse> call = Retroclient
                    .getInstance()
                    .getapi()
                    .createuser(signin);

            call.enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    try {
                        if (response.code() == 201) {
                            SignupResponse res = response.body();
                            Toast.makeText(SignupActivity.this, res.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(SignupActivity.this, OtpActivity.class);
                            intent.putExtra("Token",res.getToken());
                            intent.putExtra("email",email);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.d("token", res.getToken());
                        } else {
                            String s = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray parentarray = jsonObject.getJSONArray("data");
                            JSONObject obj = parentarray.getJSONObject(0);
                            String message = obj.getString("msg");
                            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    progressbar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsignup:
                progressbar.setVisibility(View.VISIBLE);
                signup();
                break;

            case R.id.tvlogin:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}

