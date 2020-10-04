package com.users.shelp_teacher.Api;

import com.users.shelp_teacher.Request.Signup;
import com.users.shelp_teacher.Response.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface api {

    @PUT("signup")
    Call<SignupResponse> createuser(@Body Signup create);
}
