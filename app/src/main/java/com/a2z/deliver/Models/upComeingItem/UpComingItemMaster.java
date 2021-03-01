package com.a2z.deliver.models.upComeingItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpComingItemMaster {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List <UpComingItemDetails> upComingItemDetailslist = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List <UpComingItemDetails> getUpComingItemDetailslist() {
        return upComingItemDetailslist;
    }

    public void setUpComingItemDetailslist(List <UpComingItemDetails> upComingItemDetailslist) {
        this.upComingItemDetailslist = upComingItemDetailslist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
