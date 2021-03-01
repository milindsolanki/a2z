package com.a2z.deliver.models.estimationRate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetEstimationRateResult implements Serializable {
    @SerializedName("before30Rate")
    @Expose
    private String before30Rate;
    @SerializedName("after30Rate")
    @Expose
    private String after30Rate;
    @SerializedName("minFare")
    @Expose
    private String minFare;
    @SerializedName("baseFare")
    @Expose
    private String baseFare;
    @SerializedName("cancellation")
    @Expose
    private String cancellation;
    @SerializedName("grossWeight")
    @Expose
    private String grossWeight;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;

    public String getBefore30Rate() {
        return before30Rate;
    }

    public void setBefore30Rate(String before30Rate) {
        this.before30Rate = before30Rate;
    }

    public String getAfter30Rate() {
        return after30Rate;
    }

    public void setAfter30Rate(String after30Rate) {
        this.after30Rate = after30Rate;
    }

    public String getMinFare() {
        return minFare;
    }

    public void setMinFare(String minFare) {
        this.minFare = minFare;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getCancellation() {
        return cancellation;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
