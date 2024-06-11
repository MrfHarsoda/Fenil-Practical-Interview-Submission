package com.practical.fenilredwhitepractical.apiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatApiClient {
    public static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                   .baseUrl("https://generativelanguage.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                   .build();
        }

        return retrofit;
    }
}
