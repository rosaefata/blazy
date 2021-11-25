package com.example.blazy.ui.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blazy.util.SessionManagerUtil;

public class BaseActivity extends AppCompatActivity {

    public void checkSession(String token){
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this);
        if (!isAllowed) {
            SessionManagerUtil.getInstance().endUserSession(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
