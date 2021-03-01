package com.a2z.deliver.models.toPickUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToPickUpMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ToPickUpDetails> pickUpDetails = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<ToPickUpDetails> getPickUpDetails() {
        return pickUpDetails;
    }

    public void setPickUpDetails(List<ToPickUpDetails> pickUpDetails) {
        this.pickUpDetails = pickUpDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
