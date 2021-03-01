package com.a2z.deliver.activities.sendItem;

import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.models.estimationRate.GetEstimationRateMaster;

public interface SendView {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getEstimationRate(GetEstimationRateMaster getEstimationRateMaster);

}
