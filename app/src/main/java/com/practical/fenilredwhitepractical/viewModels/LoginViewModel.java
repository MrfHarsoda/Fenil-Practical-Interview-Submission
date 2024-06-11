package com.practical.fenilredwhitepractical.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.practical.fenilredwhitepractical.dashboard.HomePageActivity;
import com.practical.fenilredwhitepractical.dashboard.LoginActivity;
import com.practical.fenilredwhitepractical.dashboard.RegisterActivity;
import com.practical.fenilredwhitepractical.pref.AccountProvider;
import com.practical.fenilredwhitepractical.utils.Loader;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    FirebaseAuth auth;
    AccountProvider accountProvider;

    public LoginViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void onLoginClicked(Context context) {
        accountProvider = new AccountProvider(context);

        if (username.getValue() != null && password.getValue() != null) {

            Loader.showDialog(context,true);

            auth.signInWithEmailAndPassword(username.getValue(), password.getValue())
                    .addOnCompleteListener(task -> {
                        Loader.showDialog(context,false);
                        if (task.isSuccessful()) {
                            username.setValue("");
                            password.setValue("");
                            accountProvider.setLoggedIn(true);
                            context.startActivity(new Intent(context, HomePageActivity.class));
                            if (!(context instanceof Activity)) {
                                return;
                            }
                            ((Activity)context).finish();
                        } else {
                            Toast.makeText(context, "Login failed, try again", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: "+e.getMessage() );
                            Toast.makeText(context, "Login failed, try again", Toast.LENGTH_SHORT).show();
                            Loader.showDialog(context,false);
                        }
                    });
        } else {
            Toast.makeText(context, "Invalid credential", Toast.LENGTH_SHORT).show();
        }
    }


    public void navigateForRegister(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    public void logout(Activity context) {
        accountProvider = new AccountProvider(context);
        auth.signOut();
        accountProvider.setLoggedIn(false);
        Toast.makeText(context, "Logout successfully", Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, LoginActivity.class));
        context.finish();
    }

}