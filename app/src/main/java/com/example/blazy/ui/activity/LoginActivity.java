package com.example.blazy.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.blazy.R;
import com.example.blazy.databinding.ActivityLoginBinding;
import com.example.blazy.databinding.FailLoginDialogBinding;
import com.example.blazy.model.Product;
import com.example.blazy.model.apiresponse.userlogin.Data;
import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;
import com.example.blazy.repository.ProductRepository;
import com.example.blazy.ui.LoadingDialog;
import com.example.blazy.util.SessionManagerUtil;
import com.example.blazy.viewmodel.ProductViewModel;
import com.example.blazy.viewmodel.UserLoginViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private UserLoginResponse userLoginResponse;
    private UserLoginViewModel userLoginViewModel;
    private FailLoginDialogBinding failLoginDialogBinding;
    private Dialog failLoginDailog;
    private View view;
    private LoadingDialog loadingDialog;
    private ProductViewModel productViewModel;

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
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        activityLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = activityLoginBinding.emailEditText.getText().toString();
                String password = activityLoginBinding.passwordEditText.getText().toString();
                String token = "";

                loadingDialog.showLoadingDialog();

                processLogin(username, password);


            }
        });
    }

    private void processLogin(String username, String password){
        userLoginViewModel.userLogin(username, password).observe(this, user -> {
            if(isValidLogin(user)) login(user.getToken(), user.getData());
            productViewModel.setAllProduct(true, new ProductRepository.DataReadyListener() {
                @Override
                public void onDataReady(LiveData<List<Product>> products) {

                }
            });
        });
    }



    private boolean isValidLogin(UserLoginResponse response){

        loadingDialog.dismissDialog();
        if(!response.getStatus()){
            showFailLoginDialog(R.string.invalid_credential);
            return false;
        }else{
            return true;
        }


    }



    private void login(String token, Data userData){
        backgroundThread.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
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



    private void storeSession(String token, Data userData){
        SessionManagerUtil.getInstance().storeSession(this, token, userData);
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }



}