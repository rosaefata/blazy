package com.example.blazy.ui;

import android.app.Activity;
import android.app.Dialog;

import com.example.blazy.databinding.LoadingDialogBinding;
import com.example.blazy.util.SessionManagerUtil;

public class LoadingDialog {

    private Activity activity;
    private Dialog dialog;
    private LoadingDialogBinding loadingDialogBinding;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }


    public void showLoadingDialog(){
        dialog = new Dialog(activity);
        loadingDialogBinding = LoadingDialogBinding.inflate(activity.getLayoutInflater());
        dialog.setContentView(loadingDialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
