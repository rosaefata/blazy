package com.example.blazy.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WishlistRepository {
    private WishlistDao wishlistDao;
    private LiveData<List<Wishlist>> listFav;

    public WishlistRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        wishlistDao = db.wishlistDao();
        listFav = wishlistDao.getWishlist();
    }

    LiveData<List<Wishlist>> getListFav(){
        return listFav;
    }

    public void insert(Wishlist fav){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wishlistDao.insert(fav);
            }
        });
    }

    public void delete(Wishlist fav){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wishlistDao.delete(fav);
            }
        });
    }
}
