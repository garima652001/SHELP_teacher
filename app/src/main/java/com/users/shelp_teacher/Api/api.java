package com.users.shelp_teacher.Api;

import com.users.shelp_teacher.Request.Login;
import com.users.shelp_teacher.Request.Resendotp;
import com.users.shelp_teacher.Request.Signup;
import com.users.shelp_teacher.Request.Verify;
import com.users.shelp_teacher.Response.LoginResponse;
import com.users.shelp_teacher.Response.OtpResponse;
import com.users.shelp_teacher.Response.ResendOtpResponse;
import com.users.shelp_teacher.Response.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface api {

    @PUT("signup")
    Call<SignupResponse> createuser(@Body Signup create);

    @POST("signup/otp")
    Call<OtpResponse> verifymail(@Body Verify otp_ver);

    @POST("signup/otp-resend")
    Call<ResendOtpResponse> resend(@Body Resendotp resend);

    @POST("login")
    Call<LoginResponse> loginUser (@Body Login log);
}
