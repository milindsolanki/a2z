package com.a2z.deliver.models.estimationRate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetEstimationRateMaster implements Serializable{

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private GetEstimationRateResult getEstimationRateResult;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public GetEstimationRateResult getGetEstimationRateResult() {
        return getEstimationRateResult;
    }

    public void setGetEstimationRateResult(GetEstimationRateResult getEstimationRateResult) {
        this.getEstimationRateResult = getEstimationRateResult;
    }
}
