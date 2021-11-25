package com.example.blazy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.blazy.model.apiresponse.userlogin.UserLoginResponse;
import com.example.blazy.repository.UserLoginRepository;

public class UserLoginViewModel extends AndroidViewModel {

    private UserLoginRepository userLoginRepository;
    private MutableLiveData<UserLoginResponse> userLogin = new MutableLiveData<>();

    public UserLoginViewModel(@NonNull Application application) {
        super(application);
        userLoginRepository = new UserLoginRepository(application);

    }

    public MutableLiveData<UserLoginResponse> userLogin(String username, String pass){
        return userLoginRepository.userLogin(username, pass);
    }
}
