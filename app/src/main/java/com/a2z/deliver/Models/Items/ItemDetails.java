package com.a2z.deliver.models.items;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetails {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("totalDistance")
    @Expose
    private String totalDistance;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemValue")
    @Expose
    private String itemValue;
    @SerializedName("itemFits")
    @Expose
    private String itemFits;
    @SerializedName("itemWidth")
    @Expose
    private String itemWidth;
    @SerializedName("itemHeight")
    @Expose
    private String itemHeight;
    @SerializedName("itemImage")
    @Expose
    private String itemImage;
    @SerializedName("itemLength")
    @Expose
    private String itemLength;
    @SerializedName("grossWeight")
    @Expose
    private String grossWeight;
    @SerializedName("sendingTime")
    @Expose
    private String sendingTime;
    @SerializedName("receivingTime")
    @Expose
    private String receivingTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalCost")
    @Expose
    private String totalCost;
    @SerializedName("extraDetails")
    @Expose
    private String extraDetails;
    @SerializedName("pickupAddress")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("deliveryAddress")
    @Expose
    private DeliveryAddress deliveryAddress;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("availableDrivers")
    @Expose
    private String availableDrivers;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("driverStatus")
    @Expose
    private String driverStatus;
    @SerializedName("documents")
    @Expose
    private String documents;
    @SerializedName("whoWillPass")
    @Expose
    private String whoWillPass;
    @SerializedName("passName")
    @Expose
    private String passName;
    @SerializedName("passMobile")
    @Expose
    private String passMobile;
    @SerializedName("passtEmailId")
    @Expose
    private String passtEmailId;
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
    @SerializedName("deliveryCost")
    @Expose
    private String deliveryCost;
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
    @SerializedName("deliveryDateTime")
    @Expose
    private String deliveryDateTime;


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

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
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

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemFits() {
        return itemFits;
    }

    public void setItemFits(String itemFits) {
        this.itemFits = itemFits;
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

    public String getItemLength() {
        return itemLength;
    }

    public void setItemLength(String itemLength) {
        this.itemLength = itemLength;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailableDrivers() {
        return availableDrivers;
    }

    public void setAvailableDrivers(String availableDrivers) {
        this.availableDrivers = availableDrivers;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
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

    public String getPasstEmailId() {
        return passtEmailId;
    }

    public void setPasstEmailId(String passtEmailId) {
        this.passtEmailId = passtEmailId;
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

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
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

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
}