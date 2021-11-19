package com.example.blazy.api.userlogin;

import com.example.blazy.api.RetrofitInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginRetrofitInstance {

    private UserLoginApiEndpointInterface API;

    public UserLoginRetrofitInstance(){
        OkHttpClient client = new RetrofitInterceptor().getClient();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserLoginApiEndpointInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        API = retrofit.create(UserLoginApiEndpointInterface.class);
    }

    public static Retrofit newInstance(){
        OkHttpClient client = new RetrofitInterceptor().getClient();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserLoginApiEndpointInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit;
    }

    public UserLoginApiEndpointInterface getAPI(){
        return API;
    }

    public static UserLoginApiEndpointInterface getAPIV2(){
        return newInstance().create(UserLoginApiEndpointInterface.class);
    }
}
