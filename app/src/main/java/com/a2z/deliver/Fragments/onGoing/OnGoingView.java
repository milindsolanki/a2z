package com.a2z.deliver.fragments.onGoing;

import com.a2z.deliver.models.mySend.OnGoingItemsMaster;

public interface OnGoingView {
    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getOnGoingItems(OnGoingItemsMaster onGoingItemsMaster);
}
