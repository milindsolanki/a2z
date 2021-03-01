package com.a2z.deliver.models.onTheWay;

import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.PickupAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnTheWayDetails implements Serializable{
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("itemImage")
    @Expose
    private String itemImage;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("grossWeight")
    @Expose
    private String grossWeight;
    @SerializedName("itemHeight")
    @Expose
    private String itemHeight;
    @SerializedName("itemLength")
    @Expose
    private String itemLength;
    @SerializedName("itemWidth")
    @Expose
    private String itemWidth;
    @SerializedName("totalDistance")
    @Expose
    private String totalDistance;
    @SerializedName("pickupAddress")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("deliveryAddress")
    @Expose
    private DeliveryAddress deliveryAddress;
    @SerializedName("whoWillPass")
    @Expose
    private String whoWillPass;
    @SerializedName("passName")
    @Expose
    private String passName;
    @SerializedName("passMobile")
    @Expose
    private String passMobile;
    @SerializedName("whoWillMeet")
    @Expose
    private String whoWillMeet;
    @SerializedName("meetName")
    @Expose
    private String meetName;
    @SerializedName("meetMobile")
    @Expose
    private String meetMobile;
    @SerializedName("extraDetails")
    @Expose
    private String extraDetails;
    @SerializedName("sendingTime")
    @Expose
    private String sendingTime;
    @SerializedName("receivingTime")
    @Expose
    private String receivingTime;
    @SerializedName("deliveryCost")
    @Expose
    private String deliveryCost;
    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;
    @SerializedName("pickupCode")
    @Expose
    private String pickupCode;
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("passEmail")
    @Expose
    private String passEmail;
    @SerializedName("meetEmail")
    @Expose
    private String meetEmail;
    @SerializedName("senderImage")
    @Expose
    private String senderImage;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("senderContact")
    @Expose
    private String senderContact;
    @SerializedName("senderEmail")
    @Expose
    private String senderEmail;
    @SerializedName("totalRating")
    @Expose
    private Integer totalRating;
    @SerializedName("notificationCounter")
    @Expose
    private Integer notificationCounter;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(String itemHeight) {
        this.itemHeight = itemHeight;
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

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
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

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
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

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPassEmail() {
        return passEmail;
    }

    public void setPassEmail(String passEmail) {
        this.passEmail = passEmail;
    }

    public String getMeetEmail() {
        return meetEmail;
    }

    public void setMeetEmail(String meetEmail) {
        this.meetEmail = meetEmail;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSenderContact() {
        return senderContact;
    }

    public void setSenderContact(String senderContact) {
        this.senderContact = senderContact;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getNotificationCounter() {
        return notificationCounter;
    }

    public void setNotificationCounter(Integer notificationCounter) {
        this.notificationCounter = notificationCounter;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }
}
