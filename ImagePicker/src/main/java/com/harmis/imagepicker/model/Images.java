package com.harmis.imagepicker.model;

import java.io.Serializable;

/**
 * Created by Aprod LLC. on 6/29/2018.
 */
public class Images implements Serializable {
    String imageUrl;
    boolean isChecked;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
