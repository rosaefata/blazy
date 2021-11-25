package com.example.blazy.listener;

import com.example.blazy.model.Wishlist;

public interface WishlistListener {

    public void goToDetail(Wishlist wishlist);
    public void deleteWishlist(Wishlist wishlist);
    public void onDataNotFound();
    public void onDataFound();
}
