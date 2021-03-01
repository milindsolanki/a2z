package com.a2z.deliver.models.onGoing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnGoingItemsMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<OnGoingItemsDetails> onGoingItemsResults =null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List <OnGoingItemsDetails> getOnGoingItemsResults() {
        return onGoingItemsResults;
    }

    public void setOnGoingItemsResults(List <OnGoingItemsDetails> onGoingItemsResults) {
        this.onGoingItemsResults = onGoingItemsResults;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
