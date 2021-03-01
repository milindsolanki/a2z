package com.a2z.deliver.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginDetails implements Serializable {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("isVerify")
    @Expose
    private String isVerify;
    @SerializedName("notificationCounter")
    @Expose
    private String notificationCounter;
    @SerializedName("totalRating")
    @Expose
    private String totalRating;
    @SerializedName("randomToken")
    @Expose
    private String randomToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getNotificationCounter() {
        return notificationCounter;
    }

    public void setNotificationCounter(String notificationCounter) {
        this.notificationCounter = notificationCounter;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }
}
