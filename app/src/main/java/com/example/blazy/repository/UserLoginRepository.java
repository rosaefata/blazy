package com.example.blazy.repository;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.blazy.api.fakestore.FakeStoreApiEndpointInterface;
import com.example.blazy.api.userlogin.UserLoginApiEndpointInterface;
import com.example.blazy.api.userlogin.UserLoginRetrofitInstance;
import com.example.blazy.model.Product;
import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginRepository {

    private UserLoginApiEndpointInterface API;
    private MutableLiveData<UserLoginResponse> user = new MutableLiveData<>();

    private final ExecutorService callApiExecutor =
            Executors.newSingleThreadExecutor();
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };


    public UserLoginRepository(Application application) {
        UserLoginRetrofitInstance instance = new UserLoginRetrofitInstance();
        API = instance.getAPI();
    }

    public void userLoginApi(String username, String password){
        callApiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UserLoginResponse userLoginResponse = API.userLogin("454041184B0240FBA3AACD15A1F7A8BB", username, password).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            user.setValue(userLoginResponse);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<UserLoginResponse> userLogin(String username, String password){
        if (user.getValue() == null)
            userLoginApi(username, password);
        return user;
    }



}
