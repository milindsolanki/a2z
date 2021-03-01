package com.a2z.deliver.models.mySend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnGoingItemsMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private List<OnGoingItemsResult> onGoingItemsResults = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<OnGoingItemsResult> getResult() {
        return onGoingItemsResults;
    }

    public void setResult(List<OnGoingItemsResult> result) {
        this.onGoingItemsResults = result;
    }
}
