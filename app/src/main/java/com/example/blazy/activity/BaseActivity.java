package com.example.blazy.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blazy.util.SessionManagerUtil;

public class BaseActivity extends AppCompatActivity {

    public void checkSession(String token){
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this);
        if (!isAllowed) {
            SessionManagerUtil.getInstance().endUserSession(this);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
