package com.a2z.deliver.Models.EditLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditLocationMaster {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private EditLocationDetails editLocationDetails;
    @SerializedName("addressComponents")
    @Expose
    private AddressComponents addressComponents;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EditLocationDetails getEditLocationDetails() {
        return editLocationDetails;
    }

    public void setEditLocationDetails(EditLocationDetails editLocationDetails) {
        this.editLocationDetails = editLocationDetails;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public AddressComponents getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(AddressComponents addressComponents) {
        this.addressComponents = addressComponents;
    }
}
