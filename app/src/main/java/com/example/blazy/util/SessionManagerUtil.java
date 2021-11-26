package com.example.blazy.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.blazy.model.apiresponse.userlogin.Data;

import java.util.Calendar;
import java.util.Date;

public class SessionManagerUtil {

    public static final String SESSION_PREFERENCE = "com.example.blazy.SessionManagerUtil.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.blazy.SessionManagerUtil.SESSION_TOKEN";
    public static final String SESSION_EXPIRY_TIME = "com.example.blazy.SessionManagerUtil.SESSION_EXPIRY_TIME";
    public static final String SESSION_FLAG_LOGIN = "com.example.blazy.SessionManagerUtil.SESSION_FLAG_TOKEN";
    public static final String SESSION_USER_EMAIL = "com.example.blazy.SessionManagerUtil.SESSION_USER_EMAIL";
    public static final String SESSION_USER_FULLNAME = "com.example.blazy.SessionManagerUtil.SESSION_USER_FULLNAME";
    public static final String SESSION_USER_IMG = "com.example.blazy.SessionManagerUtil.SESSION_USER_IMG";

    //singleton
    private static SessionManagerUtil INSTANCE;
    public static SessionManagerUtil getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SessionManagerUtil();
        }
        return INSTANCE;
    }

    public void storeSession(Context context,String token, Data userData){
        SessionManagerUtil.getInstance().storeUserToken(context, token, userData);
        SessionManagerUtil.getInstance().startUserSession(context, 120);
    }

    public void startUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLoggedTime = calendar.getTime();
        calendar.setTime(userLoggedTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expiryTime = calendar.getTime();
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiresAt = new Date(getExpiryDateFromPreference(context));
        return !currentTime.after(sessionExpiresAt);
    }


    private long getExpiryDateFromPreference(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getLong(SESSION_EXPIRY_TIME, 0);
    }


    public void storeUserToken(Context context, String token, Data userData){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(SESSION_TOKEN, token);
        editor.putBoolean(SESSION_FLAG_LOGIN, true);
        editor.putString(SESSION_USER_EMAIL, userData.getEmail());
        editor.putString(SESSION_USER_FULLNAME, userData.getFullName());
        editor.putString(SESSION_USER_IMG, userData.getAvatar());
        editor.apply();
    }

    public String getUserToken(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_TOKEN, "");
    }

    public boolean isUserLoggedIn(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getBoolean(SESSION_FLAG_LOGIN, false);
    }
    
    public Data getUserData(Context context){
        String fullName = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_USER_FULLNAME, "");

        String email = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_USER_EMAIL, "");

        String img = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_USER_IMG, "");

        Data userData = new Data();
        userData.setAvatar(img);
        userData.setEmail(email);
        userData.setFullName(fullName);
        return userData;
    }

    public void endUserSession(Context context){
        clearStoredData(context);
    }

    private void clearStoredData(Context context){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}
