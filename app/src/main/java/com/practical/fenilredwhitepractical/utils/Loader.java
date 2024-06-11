package com.practical.fenilredwhitepractical.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.practical.fenilredwhitepractical.R;

public class Loader {
    static Dialog dialog = null;

    public static void showDialog(Context context,boolean isShow) {
        try {
            if (isShow) {
                dialog = new Dialog(context);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.
                        getResources().getColor(R.color.transparent)));
                dialog.setContentView(R.layout.dialog_progress);
                dialog.show();
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        } catch (Exception e) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            e.printStackTrace();
        }
    }

}
