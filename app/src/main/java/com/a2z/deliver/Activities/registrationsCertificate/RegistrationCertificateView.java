package com.a2z.deliver.activities.registrationsCertificate;

import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;

public interface RegistrationCertificateView {


    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getUpdateDrivingLicence(DrivingLicenceMaster drivingLicenceMaster);

    void getUpdateDrivingLicenceImage(GetImageMaster getImageMaster);

}