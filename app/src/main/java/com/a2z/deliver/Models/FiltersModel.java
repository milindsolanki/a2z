package com.a2z.deliver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiltersModel {
    @SerializedName("isFilter")
    @Expose
    private Integer isFilter = 0;
    @SerializedName("category")
    @Expose
    private String category;

    public Integer getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(Integer isFilter) {
        this.isFilter = isFilter;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{" +
                "isFilter:" + isFilter +
                ",category:'" + category + '\'' +
                '}';
    }


}
