package com.example.blazy.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.blazy.api.userlogin.UserLoginApiEndpointInterface;
import com.example.blazy.api.userlogin.UserLoginRetrofitInstance;
import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;

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

    //msg : FAIL -> fail call api
    public void userLoginApi(String username, String password){

        callApiExecutor.execute(new Runnable() {
            @Override
            public void run() {


               API.userLogin("454041184B0240FBA3AACD15A1F7A8BB", username, password).enqueue(new Callback<UserLoginResponse>() {
                   @Override
                   public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                       if(!response.isSuccessful()) {
                           UserLoginResponse userLoginResponse = new UserLoginResponse();
                           userLoginResponse.setStatus(false);
                           user.setValue(userLoginResponse);
                       }else{
                           user.setValue(response.body());
                       }


                   }

                   @Override
                   public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                       UserLoginResponse userLoginResponse = new UserLoginResponse();
                       userLoginResponse.setStatus(false);
                       user.setValue(userLoginResponse);
                   }
               });

            }
        });
    }

    public MutableLiveData<UserLoginResponse> userLogin(String username, String password){
        if (user.getValue() == null)
            userLoginApi(username, password);
        return user;
    }



}
