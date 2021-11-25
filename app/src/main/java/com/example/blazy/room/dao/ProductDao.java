package com.example.blazy.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.blazy.model.Product;

import java.util.List;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllProduct();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> productList);

    @Query("DELETE FROM product")
    void deleteAll();
}
