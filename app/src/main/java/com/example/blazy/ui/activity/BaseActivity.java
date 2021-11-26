package com.example.blazy.ui.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blazy.util.SessionManagerUtil;

import java.util.Calendar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        checkSession();
    }


    public void checkSession(){
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime())
                && SessionManagerUtil.getInstance().isUserLoggedIn(this);
        if (!isAllowed) {
            SessionManagerUtil.getInstance().endUserSession(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
