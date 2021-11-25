package com.example.blazy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blazy.R;
import com.example.blazy.listener.WishlistListener;
import com.example.blazy.model.Wishlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RVWishlistAdapter extends RecyclerView.Adapter<RVWishlistAdapter.vHolder>{

    public List<Wishlist> wishlist = new ArrayList<>();
    WishlistListener wishlistListener;

    public void setWishlist(List<Wishlist> wishlist, WishlistListener wishlistListener){
        this.wishlist.clear();
        this.wishlist = wishlist;
        this.wishlistListener = wishlistListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new vHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_rv,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.bind(wishlist.get(position));

        holder.cvWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistListener.goToDetail(wishlist.get(holder.getAdapterPosition()));
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(holder, holder.getAdapterPosition());
                //wishlistListener.deleteWishlist(wishlist.get(holder.getAdapterPosition()));
            }
        }
        );
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{

        public ImageView ivProdWishlist;
        public TextView tvProdWishlist;
        public TextView tvPriceWishlist;
        public ImageButton ibDelete;

        CardView cvWishlist;

        public vHolder(@NonNull View itemView) {
            super(itemView);

            ivProdWishlist = itemView.findViewById(R.id.iv_prod_wishlist);
            tvProdWishlist = itemView.findViewById(R.id.tv_prod_wishlist);
            tvPriceWishlist = itemView.findViewById(R.id.tv_price_wishlist);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            cvWishlist = itemView.findViewById(R.id.cv_wishlist);
        }

        //view binding/grouping agar jadi 1
        public void bind(Wishlist wishlist) {
            tvProdWishlist.setText(wishlist.getProdName_wishlist());
            tvPriceWishlist.setText(wishlist.getProdPrice_wishlist().toString());
            Picasso.get().load(wishlist.getImage_wishlist()).into(ivProdWishlist);
        }

    }

    public void confirmDelete(vHolder holder, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(holder.itemView.getContext());
        alert.setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wishlistListener.deleteWishlist(wishlist.get(holder.getAdapterPosition()));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }
}
