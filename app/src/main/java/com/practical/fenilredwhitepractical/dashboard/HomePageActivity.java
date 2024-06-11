package com.practical.fenilredwhitepractical.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.adapters.ChatAdapter;
import com.practical.fenilredwhitepractical.databinding.ActivityMainBinding;
import com.practical.fenilredwhitepractical.models.ChatAdapterModel;
import com.practical.fenilredwhitepractical.room.ChatDbUtils;
import com.practical.fenilredwhitepractical.viewModels.ChatViewModel;
import com.practical.fenilredwhitepractical.viewModels.LoginViewModel;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ChatViewModel chatViewModel;
    LoginViewModel loginViewModel;
    ChatAdapter chatAdapter;
    ChatDbUtils chatDbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatDbUtils = new ChatDbUtils(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setViewModel(chatViewModel);
        binding.setContext(this);
        binding.setLifecycleOwner(this);

        chatViewModel.getResult().observeForever(new Observer<ChatAdapterModel>() {
            @Override
            public void onChanged(ChatAdapterModel chatAdapterModel) {
                if (chatAdapterModel.getAnswer() != null) {
                    validateSendButton(true);
                    chatDbUtils.insertChat(chatAdapterModel);
                    chatAdapter.updateRecord(chatAdapterModel);
                } else {
                    chatAdapter.insertNewRecord(chatAdapterModel);
                }
            }
        });

        binding.rlSend.setOnClickListener(view -> {

            if (binding.etMessage.getText().toString().trim().isEmpty()) {
                binding.etMessage.setError("Write some message first");
                return;
            }
            validateSendButton(false);
            chatViewModel.onMessageSend(binding.etMessage, HomePageActivity.this);
        });

        binding.ivLoggedOut.setOnClickListener(view -> {
            binding.ivLoggedOut.setEnabled(false);
            loginViewModel.logout(HomePageActivity.this);
        });

        binding.ivHistory.setOnClickListener(view -> {
            chatViewModel.navigateToHistory(this);
        });

        initAdapter();
//        chatDbUtils.getAllChats().observeForever(new Observer<List<ChatAdapterModel>>() {
//            @Override
//            public void onChanged(List<ChatAdapterModel> chatAdapterModelList) {
//                chatAdapter.updateAdapter(chatAdapterModelList);
//            }
//        });
    }

    void initAdapter() {
        chatAdapter = new ChatAdapter(this, false);
        chatAdapter.setOnAdapterRefreshListener(new ChatAdapter.OnAdapterRefreshListener() {
            @Override
            public void onRefresh(int position) {
               binding.scroller.fullScroll(View.FOCUS_DOWN);
            }
        });
        binding.rvChats.setHasFixedSize(false);
        binding.rvChats.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.rvChats.setAdapter(chatAdapter);
    }

    void validateSendButton(boolean isEnable) {
        binding.rlSend.setEnabled(isEnable);
        binding.rlSend.setAlpha(isEnable ? 1.0f : 0.5f);
    }
}