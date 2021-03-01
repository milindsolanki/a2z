package com.a2z.deliver.activities.changePassword;

import com.a2z.deliver.models.MessageModel;

/**
 * Created by Aprod LLC. on 7/25/2018.
 */
public interface ChangePasswordView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getChangePasswordSuccess(MessageModel messageModelResponse);
}
