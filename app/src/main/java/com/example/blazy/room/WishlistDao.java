package com.example.blazy.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishlistDao {
    @Query("SELECT * FROM wishlist")
    LiveData<List<Wishlist>> getWishlist();

/*    @Query("SELECT * FROM wishlist WHERE id IN (:wishlistId)")
    LiveData<List<Wishlist>> loadAllById(int[] wishlistId);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wishlist fav);

    @Delete
    void delete(Wishlist fav);
}
