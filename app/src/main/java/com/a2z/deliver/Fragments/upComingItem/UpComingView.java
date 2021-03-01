package com.a2z.deliver.fragments.upComingItem;


import com.a2z.deliver.models.onGoing.OnGoingItemsMaster;
import com.a2z.deliver.models.upComeingItem.UpComingItemMaster;

public interface UpComingView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getUpComingItems(UpComingItemMaster upComingItemMaster);
}
