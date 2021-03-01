package com.a2z.deliver.models.toPickUp;

import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.PickupAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToPickUpDetails {
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
    @SerializedName("itemSize")
    @Expose
    private String itemSize;
    @SerializedName("itemKG")
    @Expose
    private String itemKG;
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
    @SerializedName("whoWillPassTheItemSelf")
    @Expose
    private String whoWillPassTheItemSelf;
    @SerializedName("whoWillPassTheItemNotSelfName")
    @Expose
    private String whoWillPassTheItemNotSelfName;
    @SerializedName("whoWillPassTheItemNotSelfMobileNumber")
    @Expose
    private String whoWillPassTheItemNotSelfMobileNumber;
    @SerializedName("whoWillMeetTheItemNotSelfEmail")
    @Expose
    private String whoWillMeetTheItemNotSelfEmail;
    @SerializedName("whoWillMeetTheDriverSelf")
    @Expose
    private String whoWillMeetTheDriverSelf;
    @SerializedName("whoWillMeetTheDriverNotSelfName")
    @Expose
    private String whoWillMeetTheDriverNotSelfName;
    @SerializedName("whoWillMeetTheDriverNotSelfMobileNumber")
    @Expose
    private String whoWillMeetTheDriverNotSelfMobileNumber;
    @SerializedName("whoWillMeetTheDriverNotSelfEmail")
    @Expose
    private String whoWillMeetTheDriverNotSelfEmail;
    @SerializedName("extraDetails")
    @Expose
    private String extraDetails;
    @SerializedName("sendingTime")
    @Expose
    private String sendingTime;
    @SerializedName("deliveryCost")
    @Expose
    private String deliveryCost;
    @SerializedName("promocodeDiscount")
    @Expose
    private String promocodeDiscount;
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
    @SerializedName("senderImage")
    @Expose
    private String senderImage;
    @SerializedName("senderRate")
    @Expose
    private Integer senderRate;
    @SerializedName("senderContact")
    @Expose
    private String senderContact;
    @SerializedName("senderEmail")
    @Expose
    private String senderEmail;
    @SerializedName("totalsenderRating")
    @Expose
    private Integer totalsenderRating;
    @SerializedName("utcCreatedDateTime")
    @Expose
    private String utcCreatedDateTime;
    @SerializedName("notificationCounter")
    @Expose
    private Integer notificationCounter;
    @SerializedName("receivingTime")
    @Expose
    private String receivingTime;

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

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemKG() {
        return itemKG;
    }

    public void setItemKG(String itemKG) {
        this.itemKG = itemKG;
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

    public String getWhoWillPassTheItemSelf() {
        return whoWillPassTheItemSelf;
    }

    public void setWhoWillPassTheItemSelf(String whoWillPassTheItemSelf) {
        this.whoWillPassTheItemSelf = whoWillPassTheItemSelf;
    }

    public String getWhoWillPassTheItemNotSelfName() {
        return whoWillPassTheItemNotSelfName;
    }

    public void setWhoWillPassTheItemNotSelfName(String whoWillPassTheItemNotSelfName) {
        this.whoWillPassTheItemNotSelfName = whoWillPassTheItemNotSelfName;
    }

    public String getWhoWillPassTheItemNotSelfMobileNumber() {
        return whoWillPassTheItemNotSelfMobileNumber;
    }

    public void setWhoWillPassTheItemNotSelfMobileNumber(String whoWillPassTheItemNotSelfMobileNumber) {
        this.whoWillPassTheItemNotSelfMobileNumber = whoWillPassTheItemNotSelfMobileNumber;
    }

    public String getWhoWillMeetTheItemNotSelfEmail() {
        return whoWillMeetTheItemNotSelfEmail;
    }

    public void setWhoWillMeetTheItemNotSelfEmail(String whoWillMeetTheItemNotSelfEmail) {
        this.whoWillMeetTheItemNotSelfEmail = whoWillMeetTheItemNotSelfEmail;
    }

    public String getWhoWillMeetTheDriverSelf() {
        return whoWillMeetTheDriverSelf;
    }

    public void setWhoWillMeetTheDriverSelf(String whoWillMeetTheDriverSelf) {
        this.whoWillMeetTheDriverSelf = whoWillMeetTheDriverSelf;
    }

    public String getWhoWillMeetTheDriverNotSelfName() {
        return whoWillMeetTheDriverNotSelfName;
    }

    public void setWhoWillMeetTheDriverNotSelfName(String whoWillMeetTheDriverNotSelfName) {
        this.whoWillMeetTheDriverNotSelfName = whoWillMeetTheDriverNotSelfName;
    }

    public String getWhoWillMeetTheDriverNotSelfMobileNumber() {
        return whoWillMeetTheDriverNotSelfMobileNumber;
    }

    public void setWhoWillMeetTheDriverNotSelfMobileNumber(String whoWillMeetTheDriverNotSelfMobileNumber) {
        this.whoWillMeetTheDriverNotSelfMobileNumber = whoWillMeetTheDriverNotSelfMobileNumber;
    }

    public String getWhoWillMeetTheDriverNotSelfEmail() {
        return whoWillMeetTheDriverNotSelfEmail;
    }

    public void setWhoWillMeetTheDriverNotSelfEmail(String whoWillMeetTheDriverNotSelfEmail) {
        this.whoWillMeetTheDriverNotSelfEmail = whoWillMeetTheDriverNotSelfEmail;
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

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getPromocodeDiscount() {
        return promocodeDiscount;
    }

    public void setPromocodeDiscount(String promocodeDiscount) {
        this.promocodeDiscount = promocodeDiscount;
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

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public Integer getSenderRate() {
        return senderRate;
    }

    public void setSenderRate(Integer senderRate) {
        this.senderRate = senderRate;
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

    public Integer getTotalsenderRating() {
        return totalsenderRating;
    }

    public void setTotalsenderRating(Integer totalsenderRating) {
        this.totalsenderRating = totalsenderRating;
    }

    public String getUtcCreatedDateTime() {
        return utcCreatedDateTime;
    }

    public void setUtcCreatedDateTime(String utcCreatedDateTime) {
        this.utcCreatedDateTime = utcCreatedDateTime;
    }

    public Integer getNotificationCounter() {
        return notificationCounter;
    }

    public void setNotificationCounter(Integer notificationCounter) {
        this.notificationCounter = notificationCounter;
    }

    public String getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(String receivingTime) {
        this.receivingTime = receivingTime;
    }
}
