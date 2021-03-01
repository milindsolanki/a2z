package com.a2z.deliver.activities.ItemSummary;

import com.a2z.deliver.models.driverInterested.DriverInterestedMaster;
import com.a2z.deliver.models.items.ItemDetailMaster;
import com.google.gson.JsonObject;

public interface ItemSummaryView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getItemSuccess (ItemDetailMaster itemDetailMaster);

    void getDriverInterestedSuccess (DriverInterestedMaster driverInterestedMaster);

}
