package com.example.blazy.api.fakestore;

import com.example.blazy.api.RetrofitInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FakeStoreRetrofitInstance {

    private FakeStoreApiEndpointInterface API;


    public FakeStoreRetrofitInstance (){
        OkHttpClient client = new RetrofitInterceptor().getClient();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FakeStoreApiEndpointInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        retrofit.create(FakeStoreApiEndpointInterface.class);
        API = retrofit.create(FakeStoreApiEndpointInterface.class);
    }

    public FakeStoreApiEndpointInterface getAPI(){
        return API;
    }

}
