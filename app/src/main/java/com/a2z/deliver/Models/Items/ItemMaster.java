package com.a2z.deliver.models.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ItemDetails> itemDetailsList = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
