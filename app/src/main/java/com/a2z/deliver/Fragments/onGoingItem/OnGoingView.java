package com.a2z.deliver.fragments.onGoingItem;


import com.a2z.deliver.models.onGoing.OnGoingItemsMaster;

public interface OnGoingView {
    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
  void getOnGoingItems(OnGoingItemsMaster onGoingItemsMaster);
}
