package com.example.blazy.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blazy.ui.LoadingDialog;
import com.example.blazy.ui.ProductRecyclerviewAdapter;
import com.example.blazy.ui.activity.ActivityProductDetail;
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
    private LoadingDialog loadingDialog;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        loadingDialog = new LoadingDialog(getActivity());


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false);


        loadingDialog.showLoadingDialog();
        productRecyclerviewAdapter = new ProductRecyclerviewAdapter(getContext());
        homeFragmentBinding.homeProductRv.setAdapter(productRecyclerviewAdapter);
        homeFragmentBinding.homeProductRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        homeFragmentBinding.searchView.onActionViewExpanded();


        return homeFragmentBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), allProduct -> {
            loadingDialog.dismissDialog();
            productRecyclerviewAdapter.setProductList(allProduct, this);
            filterFunction();
         });
    }

    @Override
    public void onClickItem(Product product) {
        Intent intent = new Intent(getContext(), ActivityProductDetail.class);
        intent.putExtra("key.product", product); // untuk parse data
        startActivity(intent);
    }

    @Override
    public void onDataNotFound() {
        homeFragmentBinding.textNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataFound() {
        homeFragmentBinding.textNoData.setVisibility(View.GONE);
    }

    private void filterFunction(){
        homeFragmentBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productRecyclerviewAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


}