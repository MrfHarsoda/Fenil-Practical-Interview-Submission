package com.practical.fenilredwhitepractical.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.practical.fenilredwhitepractical.models.ChatAdapterModel;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM ChatAdapterModel")
    LiveData<List<ChatAdapterModel>> getAllChats();

    @Insert
    void insertChats(ChatAdapterModel chats);

    @Query("Delete from ChatAdapterModel")
    void deleteAll();
}