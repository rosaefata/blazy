package com.example.blazy.listener;

import com.example.blazy.model.Product;
import com.example.blazy.room.Wishlist;

public interface WishlistListener {

    public void goToDetail(Wishlist wishlist);
    public void deleteWishlist(Wishlist wishlist);
}
