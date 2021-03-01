package com.a2z.deliver.activities.Profile;

import com.a2z.deliver.models.login.LoginMaster;

public interface ProfileView {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getUpdateSuccess(LoginMaster loginMaster);
}
