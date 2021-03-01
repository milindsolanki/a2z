package com.a2z.deliver.activities.drivingLicence;

import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;

public interface DrivingLicenceView {
    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getUpdateDrivingLicence(DrivingLicenceMaster drivingLicenceMaster);
    void getDrivingLicenceImage(GetImageMaster getImageMaster);
}
