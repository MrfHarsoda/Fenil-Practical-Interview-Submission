package com.practical.fenilredwhitepractical.dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.adapters.ChatAdapter;
import com.practical.fenilredwhitepractical.databinding.ActivityHistoryBinding;
import com.practical.fenilredwhitepractical.models.ChatAdapterModel;
import com.practical.fenilredwhitepractical.room.ChatDbUtils;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;
    ChatDbUtils chatDbUtils;
    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatDbUtils = new ChatDbUtils(this);

        initAdapter();

        chatDbUtils.getAllChats().observeForever(new Observer<List<ChatAdapterModel>>() {
            @Override
            public void onChanged(List<ChatAdapterModel> chatAdapterModelList) {
                chatAdapter.updateAdapter(chatAdapterModelList);
            }
        });

        binding.ivBack.setOnClickListener(view -> finish());

        binding.ivDelete.setOnClickListener(view -> {
            chatDbUtils.deleteChats();
            chatAdapter.updateAdapter(chatDbUtils.getAllChats().getValue());
        });

    }

    void initAdapter() {
        chatAdapter = new ChatAdapter(this,true);
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
}