package com.practical.fenilredwhitepractical.dashboard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.databinding.ActivityLoginBinding;
import com.practical.fenilredwhitepractical.viewModels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setViewModel(loginViewModel);
        binding.setActivityContext(this);
        binding.setLifecycleOwner(this);


    }
}