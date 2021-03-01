package com.a2z.deliver.fragments.home;

import com.a2z.deliver.models.items.ItemMaster;

public interface HomeFragmentView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getItemListSuccess(ItemMaster itemMaster);
}
