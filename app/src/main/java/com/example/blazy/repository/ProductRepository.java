package com.example.blazy.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.blazy.api.fakestore.FakeStoreApiEndpointInterface;
import com.example.blazy.api.fakestore.FakeStoreRetrofitInstance;
import com.example.blazy.model.Product;
import com.example.blazy.room.AppDatabase;
import com.example.blazy.room.dao.ProductDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private FakeStoreApiEndpointInterface API;
    private LiveData<List<Product>> listProduct = new MutableLiveData<>();
    private ProductDao productDao;

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
        AppDatabase db = AppDatabase.getDatabase(application);
        this.productDao = db.productDao();
        this.listProduct = productDao.getAllProduct();

    }

    public void deleteAllProduct(){
        Future<?> future = AppDatabase.databaseWriteExecutor.submit(() -> {
            productDao.deleteAll();
            Log.d("PRODUCT REPO", "Data deleted");
        });
    }

    public LiveData<List<Product>> getAllProducts(){
        return listProduct;
    }

    public void setProducts(boolean reload, DataReadyListener listener){
        if(reload){
            FakeStoreRetrofitInstance instance = new FakeStoreRetrofitInstance();
            API = instance.getAPI();
            API.getProductList().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    Future<?> future = AppDatabase.databaseWriteExecutor.submit(() -> {
                        productDao.deleteAll();

                        productDao.insertAll(response.body());

                        Log.d("PRODUCT REPO", "Data inserted");
                    });

                    try {
                        future.get();
                        if (future.isDone())
                            listener.onDataReady(listProduct);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        }else{
            listener.onDataReady(listProduct);
        }
    }


        public interface DataReadyListener{
            void onDataReady(LiveData<List<Product>> products);
        }

}
