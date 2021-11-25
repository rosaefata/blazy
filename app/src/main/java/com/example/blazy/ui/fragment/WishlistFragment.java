package com.example.blazy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blazy.R;
import com.example.blazy.ui.RVWishlistAdapter;
import com.example.blazy.ui.activity.ActivityProductDetail;
import com.example.blazy.listener.WishlistListener;
import com.example.blazy.model.Product;
import com.example.blazy.model.Wishlist;
import com.example.blazy.viewmodel.WishlistViewModel;

public class WishlistFragment extends Fragment implements WishlistListener {

    private RecyclerView rvWishlist;
    private View view;
    TextView text;

    public WishlistViewModel wishlistViewModel;

    public static WishlistFragment newInstance() {
        WishlistFragment fragment = new WishlistFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wishlistViewModel = new WishlistViewModel(getActivity().getApplication());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RVWishlistAdapter rvWishlistAdapter = new RVWishlistAdapter();
        rvWishlist = view.findViewById(R.id.rv_wishlist);

        rvWishlist.setAdapter(rvWishlistAdapter);
        rvWishlist.setLayoutManager(new LinearLayoutManager(getContext()));

        wishlistViewModel.getWishlist().observe(getViewLifecycleOwner(), wishlist -> {
            rvWishlistAdapter.setWishlist(wishlist, this);
            if(wishlist.isEmpty()){
                onDataNotFound();
            }else {
                onDataFound();
            }
        });

        text = view.findViewById(R.id.text_no_data_wl);
    }

    @Override
    public void goToDetail(Wishlist wishlist) {
        Product product = new Product();

        product.setId(wishlist.getId());
        product.setTitle(wishlist.getProdName_wishlist());
        product.setImage(wishlist.getImage_wishlist());
        product.setDescription(wishlist.getProdDesc_wishlist());
        product.setPrice(wishlist.getProdPrice_wishlist());

        Intent intent = new Intent(requireActivity(), ActivityProductDetail.class);
        intent.putExtra("key.product", product);

        startActivity(intent);
    }

    @Override
    public void onDataFound() {

        text.setVisibility(View.GONE);
    }

    @Override
    public void onDataNotFound() {
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteWishlist(Wishlist wishlist) {
        wishlistViewModel.delete(wishlist);
    }
}
