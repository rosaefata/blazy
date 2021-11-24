package com.example.blazy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blazy.databinding.ProductCardViewBinding;
import com.example.blazy.listener.ProductListener;
import com.example.blazy.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerviewAdapter extends RecyclerView.Adapter<ProductRecyclerviewAdapter.ProductCardHolder> {
    private Context context;
    private List<Product> productList;
    private ProductCardViewBinding productCardViewBinding;
    private ProductListener productListener;

    public ProductRecyclerviewAdapter(Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
    }

    public void setProductList(List<Product> list, ProductListener productListener){
        this.productList.clear();
        this.productList = list;
        this.productListener= productListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productCardViewBinding = ProductCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ProductRecyclerviewAdapter.ProductCardHolder viewHolder = new ProductRecyclerviewAdapter.ProductCardHolder(productCardViewBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardHolder holder, int position) {
        Picasso.get().load(productList.get(position).getImage()).into(holder.productImage);

        String productName = productList.get(position).getTitle();
        if(productList.get(position).getTitle().length() > 30){
            productName = productList.get(position).getTitle().substring(0, 30) + "...";
        }
        holder.productName.setText(productName);
        holder.productPrice.setText("$" + productList.get(position).getPrice().toString());

        //action saat item di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.onClickItem(productList.get(holder.getAdapterPosition())); //ambil id position item yg di klik
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductCardHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice;
        public ProductCardHolder(ProductCardViewBinding productCardViewBinding) {
            super(productCardViewBinding.getRoot());
            productImage = productCardViewBinding.productImage;
            productName = productCardViewBinding.productNameText;
            productPrice = productCardViewBinding.productPriceText;
        }
    }
}
