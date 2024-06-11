package com.practical.fenilredwhitepractical.dashboard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.databinding.ActivityRegisterBinding;
import com.practical.fenilredwhitepractical.viewModels.LoginViewModel;
import com.practical.fenilredwhitepractical.viewModels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.setViewModel(registerViewModel);
        binding.setContext(this);
        binding.setLifecycleOwner(this);
    }
}