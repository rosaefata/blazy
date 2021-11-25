package com.example.blazy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blazy.model.Wishlist;
import com.example.blazy.repository.WishlistRepository;

import java.util.List;

public class WishlistViewModel extends AndroidViewModel {
    private WishlistRepository wishRepository;
    private final LiveData<List<Wishlist>> listFav;

    public WishlistViewModel(@NonNull Application application) {
        super(application);
        wishRepository = new WishlistRepository(application);
        listFav = wishRepository.getListFav();
    }

    public LiveData<List<Wishlist>> getWishlist(){
        return listFav;
    }
    public void delete(Wishlist fav){
        wishRepository.delete(fav);
    }
}
