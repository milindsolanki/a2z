package com.a2z.deliver.models.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private SendOtpDetails sendOtpDetails;

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

    public SendOtpDetails getSendOtpDetails() {
        return sendOtpDetails;
    }

    public void setSendOtpDetails(SendOtpDetails sendOtpDetails) {
        this.sendOtpDetails = sendOtpDetails;
    }
}
