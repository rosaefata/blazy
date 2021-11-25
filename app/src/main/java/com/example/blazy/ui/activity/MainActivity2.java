package com.example.blazy.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blazy.R;
import com.example.blazy.databinding.ActivityMain2Binding;
import com.example.blazy.ui.fragment.HomeFragment;
import com.example.blazy.ui.fragment.ProfileFragment;
import com.example.blazy.ui.fragment.WishlistFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends BaseActivity {

    ActivityMain2Binding activityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());
        getSupportActionBar().hide();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_frame, HomeFragment.newInstance())
                    .commitNow();
        }

        activityBinding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.wishlist:
                        selectedFragment = WishlistFragment.newInstance();
                        break;
                    case R.id.profile:
                        selectedFragment = ProfileFragment.newInstance();
                        break;

                }

                if(selectedFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, selectedFragment).commit();
                }
                return true;
            }
        });

    }
}