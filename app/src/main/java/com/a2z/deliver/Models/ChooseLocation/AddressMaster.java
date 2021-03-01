package com.a2z.deliver.models.chooseLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressMaster implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<AddressDetail> addressList;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List <AddressDetail> getAddressList() {
        return addressList;
    }

    public void setAddressList(List <AddressDetail> addressList) {
        this.addressList = addressList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
