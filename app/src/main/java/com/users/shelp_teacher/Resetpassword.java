package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Resetpass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resetpassword extends AppCompatActivity {

    EditText et_pass, et_cpass;
    String pass, cpass, email;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        et_pass = findViewById(R.id.newpassword);
        et_cpass = findViewById(R.id.confirmpassword);
        btn_reset = findViewById(R.id.btn_reset);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = et_pass.getText().toString();
                cpass = et_cpass.getText().toString();
                if (pass.isEmpty()) {
                    et_pass.setError("Password is required");
                    et_pass.requestFocus();
                    return;
                }
                if (pass.length() < 5) {
                    et_pass.setError("Password must be atleast 5 characters long");
                    et_pass.requestFocus();
                    return;
                }

                if (!pass.equals(cpass)) {
                    et_cpass.setError("Passwords do not match");
                    et_cpass.requestFocus();
                    return;
                } else {
                    Resetpass reset = new Resetpass(email, pass, cpass);
                    Call<ResponseBody> call = Retroclient
                            .getInstance()
                            .getapi()
                            .resetpass(reset);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (!response.isSuccessful()) {
                                assert response.errorBody() != null;
                                try {
                                    Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    String s = response.body().string();
                                    JSONObject jsonObject = new JSONObject(s);
                                    String msg =jsonObject.getString("messsage");
                                    Toast.makeText(Resetpassword.this, msg, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Resetpassword.this, SignupActivity.class);
                                    finish();
                                    startActivity(intent);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }
}
