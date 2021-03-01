package com.a2z.deliver.activities.login;


import com.a2z.deliver.models.forgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.models.login.LoginMaster;
import com.google.gson.JsonObject;

public interface LoginView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getLoginSuccess(LoginMaster loginMaster);

    void getForgetPasswordSuccess(ForgotPasswordDetails forgotPasswordDetails);

    void getForgotPasswordParams(JsonObject params);
}
