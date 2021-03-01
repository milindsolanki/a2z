package com.a2z.deliver.Models.EditLocation;

import com.a2z.deliver.Models.ChooseLocation.GoogleAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditLocationDetails {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("addressId")
    @Expose
    private String addressId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("googleAddress")
    @Expose
    private GoogleAddress googleAddress;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("addressType")
    @Expose
    private String addressType;
    @SerializedName("fullAddress")
    @Expose
    private String fullAddress;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public GoogleAddress getGoogleAddress() {
        return googleAddress;
    }

    public void setGoogleAddress(GoogleAddress googleAddress) {
        this.googleAddress = googleAddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
