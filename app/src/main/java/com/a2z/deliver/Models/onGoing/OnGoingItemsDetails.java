package com.a2z.deliver.models.onGoing;

import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.PickupAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OnGoingItemsDetails {
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("pickupAddress")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("deliveryAddress")
    @Expose
    private DeliveryAddress deliveryAddress;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemName")
    @Expose
    private String itemName;
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
    @SerializedName("passEmailId")
    @Expose
    private String passEmailId;
    @SerializedName("whoWillMeet")
    @Expose
    private String whoWillMeet;
    @SerializedName("meetName")
    @Expose
    private String meetName;
    @SerializedName("meetMobile")
    @Expose
    private String meetMobile;
    @SerializedName("meetEmailId")
    @Expose
    private String meetEmailId;
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
    @SerializedName("orderTransactionNumer")
    @Expose
    private String orderTransactionNumer;
    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("deliveryDateTime")
    @Expose
    private String deliveryDateTime;
    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pickupCode")
    @Expose
    private String pickupCode;
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverImage")
    @Expose
    private String driverImage;
    @SerializedName("currentRating")
    @Expose
    private String currentRating;
    @SerializedName("manufactureName")
    @Expose
    private String manufactureName;
    @SerializedName("totalRating")
    @Expose
    private String totalRating;

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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public PickupAddress getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(PickupAddress pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getPassEmailId() {
        return passEmailId;
    }

    public void setPassEmailId(String passEmailId) {
        this.passEmailId = passEmailId;
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

    public String getMeetMobile() {
        return meetMobile;
    }

    public void setMeetMobile(String meetMobile) {
        this.meetMobile = meetMobile;
    }

    public String getMeetEmailId() {
        return meetEmailId;
    }

    public void setMeetEmailId(String meetEmailId) {
        this.meetEmailId = meetEmailId;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public String getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(String currentRating) {
        this.currentRating = currentRating;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

}
