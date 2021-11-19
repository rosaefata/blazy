package com.example.blazy.api.fakestore;

import com.example.blazy.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FakeStoreApiEndpointInterface {

    String BASE_URL = "https://fakestoreapi.com/";

    @GET("/products")
    Call<List<Product>> getProductList();
}
