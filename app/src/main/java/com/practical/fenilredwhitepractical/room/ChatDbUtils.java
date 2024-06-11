package com.practical.fenilredwhitepractical.room;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.practical.fenilredwhitepractical.models.ChatAdapterModel;

import java.util.List;

public class ChatDbUtils {
    ChatDao chatDao;

    public ChatDbUtils(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "chats_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        chatDao = db.getChatsDao();
    }

    public void insertChat(ChatAdapterModel chatAdapterModel) {
        chatDao.insertChats(chatAdapterModel);
    }

    public void deleteChats() {
        chatDao.deleteAll();
    }

    public LiveData<List<ChatAdapterModel>> getAllChats() {
        return chatDao.getAllChats();
    }
}
