package com.example.blazy.api.userlogin;

import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserLoginApiEndpointInterface {

    String BASE_URL = "https://talentpool.oneindonesia.id/";


    @FormUrlEncoded
    @POST("/api/user/login")
    Call<UserLoginResponse> userLogin(@Header("X-API-KEY") String apiKey, @Field("username") String username, @Field("password") String password);



}
