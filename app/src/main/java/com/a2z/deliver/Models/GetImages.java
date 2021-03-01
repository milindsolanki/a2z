package com.a2z.deliver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetImages implements Serializable{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("firstImageName")
    @Expose
    private String firstImageName;
    @SerializedName("firstImage")
    @Expose
    private String firstImage;
    @SerializedName("secondImageName")
    @Expose
    private String secondImageName;
    @SerializedName("secondImage")
    @Expose
    private String secondImage;
    @SerializedName("thirdImageName")
    @Expose
    private String thirdImageName;
    @SerializedName("thirdImage")
    @Expose
    private String thirdImage;
    @SerializedName("fourthImageName")
    @Expose
    private String fourthImageName;
    @SerializedName("fourthImage")
    @Expose
    private String fourthImage;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getFirstImageName() {
        return firstImageName;
    }

    public void setFirstImageName(String firstImageName) {
        this.firstImageName = firstImageName;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getSecondImageName() {
        return secondImageName;
    }

    public void setSecondImageName(String secondImageName) {
        this.secondImageName = secondImageName;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }

    public String getThirdImageName() {
        return thirdImageName;
    }

    public void setThirdImageName(String thirdImageName) {
        this.thirdImageName = thirdImageName;
    }

    public String getThirdImage() {
        return thirdImage;
    }

    public void setThirdImage(String thirdImage) {
        this.thirdImage = thirdImage;
    }

    public String getFourthImageName() {
        return fourthImageName;
    }

    public void setFourthImageName(String fourthImageName) {
        this.fourthImageName = fourthImageName;
    }

    public String getFourthImage() {
        return fourthImage;
    }

    public void setFourthImage(String fourthImage) {
        this.fourthImage = fourthImage;
    }
}
