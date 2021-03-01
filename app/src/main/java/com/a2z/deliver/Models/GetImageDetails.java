package com.a2z.deliver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetImageDetails  implements Serializable{
    @SerializedName("imagename")
    @Expose
    private String imagename;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
