package com.example.blazy.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.blazy.R;
import com.example.blazy.model.Product;
import com.example.blazy.model.Wishlist;
import com.example.blazy.viewmodel.ProductDetailViewModel;
import com.squareup.picasso.Picasso;

public class ActivityProductDetail extends BaseActivity {

    public Integer productId;
    public ProductDetailViewModel prodDtlViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //untuk tombol back di action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageButton ibFav =findViewById(R.id.img_button_fav);

        Product product = (Product) getIntent().getSerializableExtra("key.product"); //buat get product nya

        TextView tvProdName = findViewById(R.id.tv_product_name);
        TextView tvProdDetail = findViewById(R.id.tv_product_detail);
        TextView tvProdPrice = findViewById(R.id.tv_product_price);
        ImageView ivProduct = findViewById(R.id.iv_product);

        //set hasil parsing ke layout
        tvProdName.setText(product.getTitle());
        tvProdDetail.setText(product.getDescription());
        tvProdPrice.setText(product.getPrice().toString());
        Picasso.get().load(product.getImage()).into(ivProduct);

        productId = product.getId();

        prodDtlViewModel = new ProductDetailViewModel(getApplication());

        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save wishlist ke room
                Wishlist wishlist = new Wishlist(product.getId(), product.getImage(), product.getTitle(), product.getPrice(), product.getDescription());

                prodDtlViewModel.insert(wishlist);
                Toast.makeText(ActivityProductDetail.this, "Added to Wishlist",Toast.LENGTH_LONG).show();

            }
        });
    }

    //untuk tombol back di action bar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}