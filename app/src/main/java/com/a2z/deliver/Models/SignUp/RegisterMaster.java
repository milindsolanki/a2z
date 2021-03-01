package com.a2z.deliver.models.signUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterMaster {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private RegisterDetails registerDetails;

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

    public RegisterDetails getRegisterDetails() {
        return registerDetails;
    }

    public void setRegisterDetails(RegisterDetails registerDetails) {
        this.registerDetails = registerDetails;
    }

}
