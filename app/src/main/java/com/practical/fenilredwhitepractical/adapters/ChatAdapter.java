package com.practical.fenilredwhitepractical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practical.fenilredwhitepractical.R;
import com.practical.fenilredwhitepractical.models.ChatAdapterModel;
import com.practical.fenilredwhitepractical.utils.TypewriterTextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    List<ChatAdapterModel> chatAdapterModelList = new ArrayList<ChatAdapterModel>();
    boolean isHistory = false;
    OnAdapterRefreshListener onAdapterRefreshListener;

    public ChatAdapter(Context context,boolean isHistory) {
        this.context = context;
        this.isHistory = isHistory;
    }

    public void setOnAdapterRefreshListener(OnAdapterRefreshListener onAdapterRefreshListener) {
        this.onAdapterRefreshListener = onAdapterRefreshListener;
    }

    public interface OnAdapterRefreshListener {
        void onRefresh(int position);
    }

    public void updateAdapter(List<ChatAdapterModel> chatAdapterModelList) {
        this.chatAdapterModelList = chatAdapterModelList;
        notifyDataSetChanged();
    }

    public void insertNewRecord(ChatAdapterModel chatAdapterModel) {
        chatAdapterModelList.add(chatAdapterModel);
        notifyItemInserted(chatAdapterModelList.size());
        onAdapterRefreshListener.onRefresh(chatAdapterModelList.size());
    }

    public void updateRecord(ChatAdapterModel chatAdapterModel) {
        chatAdapterModelList.set(chatAdapterModelList.size() - 1, chatAdapterModel);
        notifyItemChanged(chatAdapterModelList.size() - 1);
        onAdapterRefreshListener.onRefresh(chatAdapterModelList.size() - 1);
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate
                (R.layout.adapter_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatAdapterModel chatAdapterModel = chatAdapterModelList.get(position);

        holder.tvUser.setText(chatAdapterModel.getQuestion() + "");

        if (chatAdapterModel.getAnswer() != null) {

            holder.progress.setVisibility(View.GONE);
            holder.tvBot.setVisibility(View.VISIBLE);

            if (isHistory) {
                holder.tvBot.setText(chatAdapterModel.getAnswer() + "");
            } else {
                holder.tvBot.animateText(chatAdapterModel.getAnswer() + "");
            }

        } else {
            holder.progress.setVisibility(View.VISIBLE);
            holder.tvBot.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return chatAdapterModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser;
        TypewriterTextView tvBot;
        RelativeLayout rlBot, rlUser;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBot = itemView.findViewById(R.id.tv_bot);
            tvUser = itemView.findViewById(R.id.tv_user);
            rlBot = itemView.findViewById(R.id.rl_bot);
            rlUser = itemView.findViewById(R.id.rl_user);
            progress = itemView.findViewById(R.id.progress);
        }
    }
}
