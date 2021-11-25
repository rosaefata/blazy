package com.example.blazy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class ProductRecyclerviewAdapter extends RecyclerView.Adapter<ProductRecyclerviewAdapter.ProductCardHolder> implements Filterable {
    private Context context;
    private List<Product> productList;
    private List<Product> productListFiltered;
    private ProductCardViewBinding productCardViewBinding;
    private ProductListener productListener;

    public ProductRecyclerviewAdapter(Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
        this.productListFiltered = new ArrayList<>();
    }

    public void setProductList(List<Product> list, ProductListener productListener){
//        this.productList.clear();
        this.productList = list;
        this.productListener= productListener;
        productListFiltered = new ArrayList<>(productList);
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
        if(productList.get(position).getTitle().length() > 40){
            productName = productList.get(position).getTitle().substring(0, 40) + "...";
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

    @Override
    public Filter getFilter() {
        return productListFilter;
    }

    private Filter productListFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> fildteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){

                fildteredList.addAll(productListFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product item : productListFiltered){
                    if( item.getTitle().toLowerCase().contains(filterPattern)){

                        fildteredList.add(item);
                    }
                }
            }


            FilterResults results = new FilterResults();
            results.values = fildteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List)results.values);

            if(productList.isEmpty()){
                productListener.onDataNotFound();
            }else{
                productListener.onDataFound();
            }
            notifyDataSetChanged();
        }
    };
}
