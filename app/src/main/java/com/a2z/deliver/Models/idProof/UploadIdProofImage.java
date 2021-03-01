package com.a2z.deliver.models.idProof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadIdProofImage {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("myKadFrontImage")
    @Expose
    private String myKadFrontImage;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMyKadFrontImage() {
        return myKadFrontImage;
    }

    public void setMyKadFrontImage(String myKadFrontImage) {
        this.myKadFrontImage = myKadFrontImage;
    }
}


