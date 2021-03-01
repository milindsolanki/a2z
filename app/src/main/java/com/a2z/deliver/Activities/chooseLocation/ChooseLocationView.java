package com.a2z.deliver.activities.chooseLocation;

import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.models.chooseLocation.AddressMaster;

public interface ChooseLocationView {
    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getAddressList(AddressMaster addressMaster);
}
