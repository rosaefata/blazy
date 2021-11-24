package com.example.blazy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blazy.room.Wishlist;
import com.example.blazy.room.WishlistRepository;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {
    private WishlistRepository wishRepository;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        wishRepository = new WishlistRepository(application);
    }

    public void insert(Wishlist fav){
        wishRepository.insert(fav);
    }

}
