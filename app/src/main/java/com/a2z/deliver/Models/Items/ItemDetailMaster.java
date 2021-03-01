package com.a2z.deliver.models.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetailMaster {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("GetAllJob")
    @Expose
    private ItemDetails itemDetails=null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
    }
}
