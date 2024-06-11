package com.practical.fenilredwhitepractical.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.practical.fenilredwhitepractical.models.ChatAdapterModel;

@Database(entities = {ChatAdapterModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ChatDao getChatsDao();
}