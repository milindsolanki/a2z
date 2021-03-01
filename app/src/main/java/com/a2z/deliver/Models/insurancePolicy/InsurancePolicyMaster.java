package com.a2z.deliver.models.insurancePolicy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsurancePolicyMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("image2")
    @Expose
    private Object image2;
    @SerializedName("image3")
    @Expose
    private String image3;
    @SerializedName("image4")
    @Expose
    private String image4;

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

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public Object getImage2() {
        return image2;
    }

    public void setImage2(Object image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

}
