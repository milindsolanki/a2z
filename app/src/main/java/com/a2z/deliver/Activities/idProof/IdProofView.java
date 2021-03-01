package com.a2z.deliver.activities.idProof;

import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.idProof.UploadIdProofImage;

public interface IdProofView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getIdProofSuccess(UploadIdProofImage uploadIdProofImage);

    void getIdProofImage(GetImageMaster getImageMaster);

}
