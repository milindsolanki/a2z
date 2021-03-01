package com.a2z.deliver.activities.insurancePolicy;


import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.insurancePolicy.InsurancePolicyMaster;

public interface InsurancePolicyView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getInsurancePolicySuccess (InsurancePolicyMaster insurancePolicyMaster);

    void getInsurancePolicyImageSuccess (GetImageMaster getImageMaster);
}
