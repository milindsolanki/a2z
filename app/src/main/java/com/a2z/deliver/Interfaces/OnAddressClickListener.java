package com.a2z.deliver.interfaces;

import com.a2z.deliver.models.chooseLocation.AddressDetail;

public interface OnAddressClickListener {
    void onAddressClick(AddressDetail addressDetail);
    void onEditClick(AddressDetail addressDetail);
}
