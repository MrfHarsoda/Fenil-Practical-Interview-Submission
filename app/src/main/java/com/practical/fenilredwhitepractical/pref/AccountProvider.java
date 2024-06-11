package com.practical.fenilredwhitepractical.pref;

import android.content.Context;
import android.content.SharedPreferences;

public class AccountProvider {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AccountProvider(Context context) {
        sharedPreferences = context.getSharedPreferences("gemini_pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLogin) {
        editor.putBoolean("is_logged_in", isLogin);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }
}
