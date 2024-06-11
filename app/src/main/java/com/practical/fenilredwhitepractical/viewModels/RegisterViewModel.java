package com.practical.fenilredwhitepractical.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.practical.fenilredwhitepractical.dashboard.HomePageActivity;
import com.practical.fenilredwhitepractical.dashboard.RegisterActivity;
import com.practical.fenilredwhitepractical.pref.AccountProvider;
import com.practical.fenilredwhitepractical.utils.Loader;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> conPassword = new MutableLiveData<>();
    FirebaseAuth auth;
    AccountProvider accountProvider;

    public RegisterViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void onRegisterUser(Context context) {
        accountProvider = new AccountProvider(context);

        if (username.getValue() != null && password.getValue() != null
        &&  conPassword.getValue() != null) {

            if (password.getValue().length() < 6) {
                Toast.makeText(context, "Password length must be 6 character", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.getValue().toLowerCase().equals(conPassword.getValue().toLowerCase())) {
                Toast.makeText(context, "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Loader.showDialog(context,true);

            auth.createUserWithEmailAndPassword(username.getValue(), password.getValue())
                    .addOnCompleteListener(task -> {
                        Loader.showDialog(context,false);
                        if (task.isSuccessful()) {
                            username.setValue("");
                            password.setValue("");
                            conPassword.setValue("");
                            Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show();
                            if (!(context instanceof Activity)) {
                                return;
                            }
                            ((Activity)context).finish();
                        } else {
                            Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: "+e.getMessage() );
                            Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show();
                            Loader.showDialog(context,false);
                        }
                    });
        } else {
            Toast.makeText(context, "Please fill credential", Toast.LENGTH_SHORT).show();
        }
    }


}