package com.huce.imuisc.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.huce.imuisc.R;

public class LoadingDialog extends Dialog {

    View progressBar;
    View tvWait;

    public LoadingDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setContentView(R.layout.customdialog);
        progressBar = findViewById(R.id.progressBar);
        this.setCancelable(false);

    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setContentView(R.layout.customdialog);
        progressBar = findViewById(R.id.progressBar);
        this.setCancelable(false);
    }


    @Override
    public void show() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            super.show();
        } catch (Exception e) {

        }
    }

}
