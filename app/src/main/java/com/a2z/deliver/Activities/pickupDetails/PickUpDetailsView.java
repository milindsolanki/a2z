package com.a2z.deliver.activities.pickupDetails;

public interface PickUpDetailsView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getPickupDetailsSuccess();
}
