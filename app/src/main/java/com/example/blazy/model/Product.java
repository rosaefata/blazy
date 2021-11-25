package com.example.blazy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//implement serializable untuk parsing data product
@Entity(tableName = "Product")
public class Product implements Serializable {


    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("price")
    @Expose
    @ColumnInfo(name = "price")
    private Double price;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("category")
    @Expose
    @ColumnInfo(name = "category")
    private String category;

    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private String image;
//    @SerializedName("rating")
//    @Expose
//    private Rating rating; //rating di comment dulu karena serializable tidak bisa terima object Rating

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public Rating getRating() {
//        return rating;
//    }

//    public void setRating(Rating rating) {
//        this.rating = rating;
//    }
}
