package com.example.blazy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.blazy.R;
import com.example.blazy.databinding.ActivityLoginBinding;
import com.example.blazy.databinding.ActivityMain2Binding;
import com.example.blazy.fragment.HomeFragment;
import com.example.blazy.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends BaseActivity {

    ActivityMain2Binding activityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

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