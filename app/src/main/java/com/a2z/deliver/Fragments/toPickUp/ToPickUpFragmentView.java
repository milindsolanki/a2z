package com.a2z.deliver.fragments.toPickUp;
import com.a2z.deliver.models.toPickUp.ToPickUpMaster;

public interface ToPickUpFragmentView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPickUpOrderSuccess(ToPickUpMaster toPickUpMaster);
}
