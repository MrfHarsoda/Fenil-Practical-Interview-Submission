package com.practical.fenilredwhitepractical.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.practical.fenilredwhitepractical.BuildConfig;
import com.practical.fenilredwhitepractical.apiService.ChatApiClient;
import com.practical.fenilredwhitepractical.apiService.ChatServiceInterface;
import com.practical.fenilredwhitepractical.dashboard.HistoryActivity;
import com.practical.fenilredwhitepractical.dashboard.RegisterActivity;
import com.practical.fenilredwhitepractical.models.Candidate;
import com.practical.fenilredwhitepractical.models.ChatAdapterModel;
import com.practical.fenilredwhitepractical.models.ChatResponse;
import com.practical.fenilredwhitepractical.models.Content;
import com.practical.fenilredwhitepractical.models.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ChatViewModel extends ViewModel {
    public MutableLiveData<String> contentMessage = new MutableLiveData<>();
    public MutableLiveData<ChatAdapterModel> chatAdapterModelMutableLiveData = new MutableLiveData<>();
    ChatServiceInterface chatServiceInterface;

    public ChatViewModel() {
        chatServiceInterface = ChatApiClient.getClient().create(ChatServiceInterface.class);
    }

    public void onMessageSend(EditText editText, Context context) {
        if (contentMessage.getValue() != null) {

            ChatAdapterModel chatAdapterModel = new ChatAdapterModel(contentMessage.getValue(),null);
            chatAdapterModelMutableLiveData.setValue(chatAdapterModel);

            try {
                JSONObject part = new JSONObject();
                part.put("text", contentMessage.getValue());

                JSONArray parts = new JSONArray();
                parts.put(part);

                JSONObject content = new JSONObject();
                content.put("parts", parts);

                JSONArray contents = new JSONArray();
                contents.put(content);

                JSONObject requestBodyJson = new JSONObject();
                requestBodyJson.put("contents", contents);

                editText.getText().clear();

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyJson.toString());
                getChatResponse(requestBody);
            } catch (Exception e) {
                e.printStackTrace();
                chatAdapterModel.setAnswer("Something went wrong, please try again");
                chatAdapterModelMutableLiveData.setValue(chatAdapterModel);
            }
        }

    }

    void getChatResponse(RequestBody requestBody) {
        ChatAdapterModel chatAdapterModel = chatAdapterModelMutableLiveData.getValue();

        Call<ChatResponse> chatResponseCall = chatServiceInterface.getChatAnswer(
                BuildConfig.API_KEY
                , requestBody);

        chatResponseCall.enqueue(new retrofit2.Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, retrofit2.Response<ChatResponse> response) {
                Log.e("TAG", "onResponse: "+response.toString() );
                if (response.isSuccessful() && response.body() != null) {
                    Candidate candidate = response.body().getCandidates().get(0);
                    Content content = candidate.getContent();
                    Part part = content.getParts().get(0);

                    if (chatAdapterModel != null) {
                        chatAdapterModel.setAnswer(part.getText());
                    }

                    chatAdapterModelMutableLiveData.setValue(chatAdapterModel);

                    return;
                }

                if (chatAdapterModel != null) {
                    chatAdapterModel.setAnswer("Something went wrong, please try again");
                }

                chatAdapterModelMutableLiveData.setValue(chatAdapterModel);
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                t.printStackTrace();
                if (chatAdapterModel != null) {
                    chatAdapterModel.setAnswer("Something went wrong, please try again");
                }

                chatAdapterModelMutableLiveData.setValue(chatAdapterModel);
            }
        });
    }

    public void navigateToHistory(Context context) {
        context.startActivity(new Intent(context, HistoryActivity.class));
    }
    public LiveData<ChatAdapterModel> getResult() {
        return chatAdapterModelMutableLiveData;
    }
}
