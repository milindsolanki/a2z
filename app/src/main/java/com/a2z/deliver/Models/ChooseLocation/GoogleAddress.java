package com.a2z.deliver.models.chooseLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GoogleAddress implements Serializable {
    @SerializedName("fullAddress")
    @Expose
    private String fullAddress;
    @SerializedName("placeId")
    @Expose
    private String placeId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("placeName")
    @Expose
    private String placeName;
    private List <AddressComponents> addressComponents = null;

    String acLocality = "", acSubLocality = "", acSubAdminArea = "", acAdminArea = "";

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List <AddressComponents> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List <AddressComponents> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getAcLocality() {
        return acLocality;
    }

    public void setAcLocality(String acLocality) {
        this.acLocality = acLocality;
    }

    public String getAcSubLocality() {
        return acSubLocality;
    }

    public void setAcSubLocality(String acSubLocality) {
        this.acSubLocality = acSubLocality;
    }

    public String getAcSubAdminArea() {
        return acSubAdminArea;
    }

    public void setAcSubAdminArea(String acSubAdminArea) {
        this.acSubAdminArea = acSubAdminArea;
    }

    public String getAcAdminArea() {
        return acAdminArea;
    }

    public void setAcAdminArea(String acAdminArea) {
        this.acAdminArea = acAdminArea;
    }

    @Override
    public String toString() {
        return "{" +
                "fullAddress:'" + fullAddress + '\'' +
                ", placeId:'" + placeId + '\'' +
                ", country:'" + country + '\'' +
                ", state:'" + state + '\'' +
                ", city:'" + city + '\'' +
                ", placeName:'" + placeName + '\'' +
                ", acLocality:'" + acLocality + '\'' +
                ", acSubLocality:'" + acSubLocality + '\'' +
                ", acSubAdminArea:'" + acSubAdminArea + '\'' +
                ", acAdminArea:'" + acAdminArea + '\'' +
                '}';
    }
}
