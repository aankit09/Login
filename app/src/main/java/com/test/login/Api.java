package com.test.login;



import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface Api {

    @FormUrlEncoded
    @POST("register")
    Call<RegisterModelResponse> getUserSignup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("phone") String phone
    );
   }

