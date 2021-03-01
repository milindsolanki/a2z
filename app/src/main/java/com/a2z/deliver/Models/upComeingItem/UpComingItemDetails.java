package com.a2z.deliver.models.upComeingItem;

import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.PickupAddress;
import com.a2z.deliver.models.onGoing.OnGoingItemsDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpComingItemDetails implements Serializable{

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("pickupAddress")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("deliveredDatetime")
    @Expose
    private Object deliveredDatetime;
    @SerializedName("deliveryAddress")
    @Expose
    private DeliveryAddress deliveryAddress;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemFits")
    @Expose
    private String itemFits;
    @SerializedName("itemValue")
    @Expose
    private String itemValue;
    @SerializedName("grossWeight")
    @Expose
    private String grossWeight;
    @SerializedName("itemLength")
    @Expose
    private String itemLength;
    @SerializedName("itemWidth")
    @Expose
    private String itemWidth;
    @SerializedName("itemHeight")
    @Expose
    private String itemHeight;
    @SerializedName("itemImage")
    @Expose
    private String itemImage;
    @SerializedName("whoWillPass")
    @Expose
    private String whoWillPass;
    @SerializedName("passName")
    @Expose
    private String passName;
    @SerializedName("passMobile")
    @Expose
    private String passMobile;
    @SerializedName("passEmail")
    @Expose
    private String passEmail;
    @SerializedName("whoWillMeet")
    @Expose
    private String whoWillMeet;
    @SerializedName("meetName")
    @Expose
    private String meetName;
    @SerializedName("meetEmail")
    @Expose
    private String meetEmail;
    @SerializedName("meetMobile")
    @Expose
    private String meetMobile;
    @SerializedName("extraDetails")
    @Expose
    private String extraDetails;
    @SerializedName("totalDistance")
    @Expose
    private String totalDistance;
    @SerializedName("deliveryCost")
    @Expose
    private String deliveryCost;
    @SerializedName("sendingTime")
    @Expose
    private String sendingTime;
    @SerializedName("receivingTime")
    @Expose
    private String receivingTime;
    @SerializedName("orderTransactionNumer")
    @Expose
    private String orderTransactionNumer;
    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;
    @SerializedName("pickupCode")
    @Expose
    private String pickupCode;
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("availableDrivers")
    @Expose
    private Integer availableDrivers;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PickupAddress getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(PickupAddress pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Object getDeliveredDatetime() {
        return deliveredDatetime;
    }

    public void setDeliveredDatetime(Object deliveredDatetime) {
        this.deliveredDatetime = deliveredDatetime;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemFits() {
        return itemFits;
    }

    public void setItemFits(String itemFits) {
        this.itemFits = itemFits;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getItemLength() {
        return itemLength;
    }

    public void setItemLength(String itemLength) {
        this.itemLength = itemLength;
    }

    public String getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(String itemWidth) {
        this.itemWidth = itemWidth;
    }

    public String getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(String itemHeight) {
        this.itemHeight = itemHeight;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getWhoWillPass() {
        return whoWillPass;
    }

    public void setWhoWillPass(String whoWillPass) {
        this.whoWillPass = whoWillPass;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public String getPassMobile() {
        return passMobile;
    }

    public void setPassMobile(String passMobile) {
        this.passMobile = passMobile;
    }

    public String getPassEmail() {
        return passEmail;
    }

    public void setPassEmail(String passEmail) {
        this.passEmail = passEmail;
    }

    public String getWhoWillMeet() {
        return whoWillMeet;
    }

    public void setWhoWillMeet(String whoWillMeet) {
        this.whoWillMeet = whoWillMeet;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getMeetEmail() {
        return meetEmail;
    }

    public void setMeetEmail(String meetEmail) {
        this.meetEmail = meetEmail;
    }

    public String getMeetMobile() {
        return meetMobile;
    }

    public void setMeetMobile(String meetMobile) {
        this.meetMobile = meetMobile;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(String receivingTime) {
        this.receivingTime = receivingTime;
    }

    public String getOrderTransactionNumer() {
        return orderTransactionNumer;
    }

    public void setOrderTransactionNumer(String orderTransactionNumer) {
        this.orderTransactionNumer = orderTransactionNumer;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Integer getAvailableDrivers() {
        return availableDrivers;
    }

    public void setAvailableDrivers(Integer availableDrivers) {
        this.availableDrivers = availableDrivers;
    }

}
