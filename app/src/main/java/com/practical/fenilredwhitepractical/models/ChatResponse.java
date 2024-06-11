package com.practical.fenilredwhitepractical.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {

    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates;
    @SerializedName("usageMetadata")
    @Expose
    private UsageMetadata usageMetadata;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

}