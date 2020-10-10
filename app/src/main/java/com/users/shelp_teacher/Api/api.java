package com.users.shelp_teacher.Api;

import com.users.shelp_teacher.Request.Checkotp;
import com.users.shelp_teacher.Request.Login;
import com.users.shelp_teacher.Request.Mycourses;
import com.users.shelp_teacher.Request.Resendotp;
import com.users.shelp_teacher.Request.Resetotp;
import com.users.shelp_teacher.Request.Resetpass;
import com.users.shelp_teacher.Request.Signup;
import com.users.shelp_teacher.Request.Uploadvid;
import com.users.shelp_teacher.Request.Verify;
import com.users.shelp_teacher.Response.CourseResponse;
import com.users.shelp_teacher.Response.LoginResponse;
import com.users.shelp_teacher.Response.OtpResponse;
import com.users.shelp_teacher.Response.ResendOtpResponse;
import com.users.shelp_teacher.Response.SignupResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface api {

    @PUT("signup")
    Call<SignupResponse> createuser(@Body Signup create);

    @POST("signup/otp")
    Call<OtpResponse> verifymail(@Body Verify otp_ver);

    @POST("signup/otp-resend")
    Call<ResendOtpResponse> resend(@Body Resendotp resend);

    @POST("login")
    Call<LoginResponse> loginUser (@Body Login log);

    @POST("signup/resetOtp")
    Call<ResponseBody> sendotp(@Body Resetotp reset);

    @POST("signup/checkOtp")
    Call<ResponseBody> checkotp(@Body Checkotp check);

    @POST("signup/reset-password")
    Call<ResponseBody> resetpass(@Body Resetpass reset);

    @Multipart
    @POST("creator/create-course")
    Call<ResponseBody> uploadPhoto(
            @Part("name") RequestBody name,
            @Part("title") RequestBody title,
            @Part("discription") RequestBody description,
            @Part("category") RequestBody category,
            @Part("_id") RequestBody _id,
            @Part("requirement") RequestBody requirement,
            @Part("discriptionLong") RequestBody descriptionLong,
            @Part("willLearn") RequestBody willlearn,
            @Part MultipartBody.Part image
    );

    @POST("teacher/uploads")
    Call<ResponseBody> mycourses(@Body Mycourses display, @Header("Authorization") String header);

    @Multipart
    @POST("creator/videoUpload/{videocourseID}")
    Call<ResponseBody> uploadVideo(
            @Path("videocourseID") String courseid,
            @Part MultipartBody.Part video);

}
