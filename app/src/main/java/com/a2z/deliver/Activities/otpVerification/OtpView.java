package com.a2z.deliver.activities.otpVerification;

import com.a2z.deliver.models.MessageModel;

public interface OtpView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getOtpSuccess(MessageModel messageModel);

}
