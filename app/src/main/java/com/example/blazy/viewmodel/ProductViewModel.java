package com.example.blazy.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.blazy.model.Product;
import com.example.blazy.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> allProducts = new MutableLiveData<>();

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public void setAllProduct(boolean reload, ProductRepository.DataReadyListener listener){
        productRepository.setProducts(reload, listener);
    }

    public LiveData<List<Product>> getAllProducts(){
        allProducts = productRepository.getAllProducts();
        Log.d("VIEW MODEL", "getAllProducts: " + allProducts.getValue());
        return allProducts;
    }

}
