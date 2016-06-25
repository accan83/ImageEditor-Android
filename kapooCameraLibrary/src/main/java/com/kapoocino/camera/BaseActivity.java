package com.kapoocino.camera;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static ProgressDialog dialog;

    public static Dialog newLoadingDialog(Context context, String title,
                                          boolean canCancel) {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(canCancel);
        dialog.setMessage(title);
        return dialog;
    }

    public static Dialog getLoadingDialog() {
        return dialog;
    }
}