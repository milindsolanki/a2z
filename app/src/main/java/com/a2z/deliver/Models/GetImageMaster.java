package com.a2z.deliver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetImageMaster implements Serializable{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private List<GetImageDetails> getImageDetails = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List <GetImageDetails> getGetImageDetails() {
        return getImageDetails;
    }

    public void setGetImageDetails(List <GetImageDetails> getImageDetails) {
        this.getImageDetails = getImageDetails;
    }
}
