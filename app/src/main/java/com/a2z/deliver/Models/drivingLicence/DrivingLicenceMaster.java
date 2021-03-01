package com.a2z.deliver.models.drivingLicence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrivingLicenceMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("imageFront")
    @Expose
    private String imageFront;
    @SerializedName("imageBack")
    @Expose
    private String imageBack;

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

    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }
}
