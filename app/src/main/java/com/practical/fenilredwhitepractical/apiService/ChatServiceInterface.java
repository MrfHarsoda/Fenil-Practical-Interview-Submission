package com.practical.fenilredwhitepractical.apiService;

import com.practical.fenilredwhitepractical.models.ChatResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatServiceInterface {

    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    Call<ChatResponse> getChatAnswer(@Query("key") String key,@Body RequestBody requestBody);
}
