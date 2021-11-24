package com.example.blazy.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.blazy.LoadingDialog;
import com.example.blazy.R;
import com.example.blazy.api.userlogin.UserLoginRetrofitInstance;
import com.example.blazy.databinding.ActivityLoginBinding;
import com.example.blazy.databinding.FailLoginDialogBinding;
import com.example.blazy.model.apiresponse.userlogin.Data;
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
    private FailLoginDialogBinding failLoginDialogBinding;
    private Dialog failLoginDailog;
    private View view;
    private LoadingDialog loadingDialog;

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

        setDialog();
        loadingDialog = new LoadingDialog(this);
        userLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);

        activityLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = activityLoginBinding.emailEditText.getText().toString();
                String password = activityLoginBinding.passwordEditText.getText().toString();
                String token = "";

                loadingDialog.showLoadingDialog();
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
                        Log.d("RETRO", "Response = " + response);

                        if(isValidLogin(response)) login(response.body().getToken(), response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        Log.d("RETRO", "fail call api");
                        loadingDialog.dismissDialog();
                        showFailLoginDialog(R.string.login_failed);
                    }
                });

            }
        });
    }

    private boolean isValidLogin(Response<UserLoginResponse> response){

        loadingDialog.dismissDialog();
        if(!response.isSuccessful()){
            showFailLoginDialog(R.string.invalid_credential);
            return false;
        }else{
            userLoginResponse = response.body();

            boolean isUserValid =  userLoginResponse.getStatus();
            if(!isUserValid){
//                            Toast.makeText(getApplicationContext(), "Username dan password tidak boleh kosong!!", Toast.LENGTH_SHORT).show();
                showFailLoginDialog(R.string.invalid_credential);
                return false;
            }

            return true;
        }


    }

    private void login(String token, Data userData){
        backgroundThread.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
//                SystemClock.sleep(3000);
                mainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        storeSession(token, userData);
                        startHomeActivity();
                    }
                });
            }
        });
    }

    private void setDialog(){
        failLoginDailog = new Dialog(this);
        failLoginDialogBinding = FailLoginDialogBinding.inflate(getLayoutInflater());
        failLoginDailog.setContentView(failLoginDialogBinding.getRoot());
        failLoginDailog.setCanceledOnTouchOutside(true);
    }

    private void showFailLoginDialog(int errMsgId){
        failLoginDialogBinding.errorMsg.setText(errMsgId);
        failLoginDailog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void storeSession(String token, Data userData){
        SessionManagerUtil.getInstance().storeUserToken(this, token, userData);
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }



}