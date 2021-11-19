package com.example.blazy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blazy.activity.ActivityProductDetail;
import com.example.blazy.ProductRecyclerviewAdapter;
import com.example.blazy.databinding.HomeFragmentBinding;
import com.example.blazy.listener.ProductListener;
import com.example.blazy.model.Product;
import com.example.blazy.viewmodel.HomeViewModel;
import com.example.blazy.viewmodel.ProductViewModel;

public class HomeFragment extends Fragment implements ProductListener {

    private HomeViewModel mViewModel;
    private ProductViewModel productViewModel;
    private HomeFragmentBinding homeFragmentBinding;
    private RecyclerView recyclerView;
    private ProductRecyclerviewAdapter productRecyclerviewAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("HOME", "onCreateView: MASUK");
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false);

        productRecyclerviewAdapter = new ProductRecyclerviewAdapter(getContext());
        homeFragmentBinding.homeProductRv.setAdapter(productRecyclerviewAdapter);
        homeFragmentBinding.homeProductRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return homeFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), products -> {
            productRecyclerviewAdapter.setProductList(products, this);
        });
    }

    @Override
    public void onClickItem(Product product) {
        Intent intent = new Intent(getContext(), ActivityProductDetail.class);
        intent.putExtra("key.product", product); // untuk parse data
        startActivity(intent);
    }
}