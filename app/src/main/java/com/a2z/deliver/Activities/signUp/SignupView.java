package com.a2z.deliver.activities.signUp;

import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.models.otp.SendOtpMaster;

public interface SignupView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getSignUpSuccess(LoginMaster loginMaster);

    void getSendOtpSuccess(SendOtpMaster sendOtpMaster);
}
