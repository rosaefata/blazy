package com.example.blazy.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blazy.model.Product;
import com.example.blazy.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> allProducts = new MutableLiveData<>();

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public MutableLiveData<List<Product>> getAllProducts(){
        allProducts = productRepository.getAllProducts();
        Log.d("VIEW MODEL", "getAllProducts: " + allProducts.getValue());
        return allProducts;
    }

}
