package com.a2z.deliver.fragments.onTheWay;

import com.a2z.deliver.models.onTheWay.OnTheWayMaster;

public interface OnTheWayFragmentView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getOnTheWayOrdersSuccess(OnTheWayMaster theWayMaster);
}
