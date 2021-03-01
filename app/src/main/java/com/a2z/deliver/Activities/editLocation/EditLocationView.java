package com.a2z.deliver.activities.editLocation;

import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.models.chooseLocation.EditLocationMaster;

public interface EditLocationView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getEditAddress(EditLocationMaster editLocationMaster);
}
