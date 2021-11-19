package com.example.blazy.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.blazy.api.userlogin.UserLoginRetrofitInstance;
import com.example.blazy.databinding.ActivityLoginBinding;
import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;
import com.example.blazy.util.SessionManagerUtil;
import com.example.blazy.viewmodel.ProductViewModel;
import com.example.blazy.viewmodel.UserLoginViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private UserLoginResponse userLoginResponse;
    private UserLoginViewModel userLoginViewModel;

    private Executor backgroundThread = Executors.newSingleThreadExecutor();
    private Executor mainThread = new Executor() {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        userLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);

        activityLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = activityLoginBinding.emailEditText.getText().toString();
                String password = activityLoginBinding.passwordEditText.getText().toString();
                String token = "";

//                UserLoginResponse userLoginResponse =  userLoginViewModel.userLogin(username, password).getValue();
//                boolean isUserValid =  userLoginResponse.getStatus();
//                if(!isUserValid){
//                    Toast.makeText(getApplicationContext(), "Username dan password tidak boleh kosong!!", Toast.LENGTH_SHORT).show();
//                }
//
//                login(userLoginResponse.getToken());

                UserLoginRetrofitInstance.getAPIV2().userLogin("454041184B0240FBA3AACD15A1F7A8BB",username, password).enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        userLoginResponse = response.body();
                        Log.d("RETRO", "Response = " + response.body().toString());

                        boolean isUserValid =  userLoginResponse.getStatus();
                        if(!isUserValid){
                            Toast.makeText(getApplicationContext(), "Username dan password tidak boleh kosong!!", Toast.LENGTH_SHORT).show();
                        }

                        login(userLoginResponse.getToken());
                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        Log.d("RETRO", "fail call api");
                    }
                });



            }
        });
    }

    private void login(String token){
        backgroundThread.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
//                SystemClock.sleep(3000);
                mainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        storeSession(token);
                        startHomeActivity();
                    }
                });
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void storeSession(String token){
        SessionManagerUtil.getInstance().storeUserToken(this, token);
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

}