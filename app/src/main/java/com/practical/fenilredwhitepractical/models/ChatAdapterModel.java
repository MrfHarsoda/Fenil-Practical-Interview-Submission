package com.practical.fenilredwhitepractical.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatAdapterModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    String question, answer;

    public ChatAdapterModel() {}

    public ChatAdapterModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
