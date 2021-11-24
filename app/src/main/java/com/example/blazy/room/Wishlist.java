package com.example.blazy.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wishlist {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "image_wishlist")
    public String image_wishlist;

    @ColumnInfo(name = "product_name_wishlist")
    public String prodName_wishlist;

    @ColumnInfo(name = "product_price_wishlist")
    public Double prodPrice_wishlist;

    @ColumnInfo(name = "product_desc_wishlist")
    public String prodDesc_wishlist;

    public Wishlist(int id, String image_wishlist, String prodName_wishlist, Double prodPrice_wishlist, String prodDesc_wishlist) {
        this.id = id;
        this.image_wishlist = image_wishlist;
        this.prodName_wishlist = prodName_wishlist;
        this.prodPrice_wishlist = prodPrice_wishlist;
        this.prodDesc_wishlist = prodDesc_wishlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_wishlist() {
        return image_wishlist;
    }

    public void setImage_wishlist(String image_wishlist) {
        this.image_wishlist = image_wishlist;
    }

    public String getProdName_wishlist() {
        return prodName_wishlist;
    }

    public void setProdName_wishlist(String prodName_wishlist) {
        this.prodName_wishlist = prodName_wishlist;
    }

    public Double getProdPrice_wishlist() {
        return prodPrice_wishlist;
    }

    public void setProdPrice_wishlist(Double prodPrice_wishlist) {
        this.prodPrice_wishlist = prodPrice_wishlist;
    }

    public String getProdDesc_wishlist() {
        return prodDesc_wishlist;
    }

    public void setProdDesc_wishlist(String prodDesc_wishlist) {
        this.prodDesc_wishlist = prodDesc_wishlist;
    }
}
