package com.example.blazy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.blazy.R;
import com.example.blazy.fragment.HomeFragment;

public class MainActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_frame, HomeFragment.newInstance())
                    .commitNow();
        }
    }
}