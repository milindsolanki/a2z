package com.a2z.deliver.models.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryAddress implements Serializable {
    @SerializedName("fullAddress")
    @Expose
    private String fullAddress;
    @SerializedName("blockAddress")
    @Expose
    private String blockAddress;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("addressId")
    @Expose
    private String addressId;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("placeName")
    @Expose
    private String placeName;
    @SerializedName("addressType")
    @Expose
    private String addressType;
    @SerializedName("googleAddress")
    @Expose
    private GoogleAddress googleAddress;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getBlockAddress() {
        return blockAddress;
    }

    public void setBlockAddress(String blockAddress) {
        this.blockAddress = blockAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public GoogleAddress getGoogleAddress() {
        return googleAddress;
    }

    public void setGoogleAddress(GoogleAddress googleAddress) {
        this.googleAddress = googleAddress;
    }
}
