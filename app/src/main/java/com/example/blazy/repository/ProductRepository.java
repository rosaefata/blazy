package com.example.blazy.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.blazy.api.fakestore.FakeStoreApiEndpointInterface;
import com.example.blazy.api.fakestore.FakeStoreRetrofitInstance;
import com.example.blazy.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private FakeStoreApiEndpointInterface API;
    private MutableLiveData<List<Product>> allProducts = new MutableLiveData<>();

    private final ExecutorService callApiExecutor =
            Executors.newSingleThreadExecutor();
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public ProductRepository(Application application){
        FakeStoreRetrofitInstance instance = new FakeStoreRetrofitInstance();
        API = instance.getAPI();
    }

    public void getProductsFromApi(){
        callApiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> productList = API.getProductList().execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            allProducts.setValue(productList);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<List<Product>> getAllProducts(){
        if (allProducts.getValue() == null || allProducts.getValue().isEmpty())
            getProductsFromApi();
        return allProducts;
    }

}
