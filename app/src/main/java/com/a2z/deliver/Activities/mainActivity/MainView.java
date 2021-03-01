package com.a2z.deliver.activities.mainActivity;


import com.a2z.deliver.models.login.LoginMaster;
import com.google.gson.JsonObject;

public interface MainView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getLoginSuccess(LoginMaster loginMaster);

    void onFacebookSuccess(JsonObject params);

    void onGoogleSuccess(JsonObject params);

}
