package com.a2z.deliver.activities.changePassword;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aprod LLC. on 7/25/2018.
 */
public class ChangePasswordPresenter {

    private final Service service;
    private final ChangePasswordView view;
    private CompositeSubscription subscriptions;
    private Activity activity;

    public ChangePasswordPresenter(Activity activity, Service service, ChangePasswordView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public boolean isValidate(String newpassword, String confirmpassowrd, EditText etNewpwd, EditText etConfirmpwd){
        if (TextUtils.isEmpty( newpassword )) {
            CommonUtils.setEditTextError( etNewpwd, activity.getResources().getString( R.string.enterpassword ) );
            return false;
        } else if (TextUtils.isEmpty( confirmpassowrd )) {
            CommonUtils.setEditTextError( etConfirmpwd, activity.getResources().getString( R.string.enterpassword ) );
            return false;
        } else if (newpassword.length() < 8) {
            CommonUtils.setEditTextError( etNewpwd, activity.getResources().getString( R.string.minpassword ) );
            return false;
        } else if (confirmpassowrd.length() < 8) {
            CommonUtils.setEditTextError( etConfirmpwd, activity.getResources().getString( R.string.minpassword ) );
            return false;
        } else if (!newpassword.equals( confirmpassowrd )) {
            CommonUtils.makeToast( activity, activity.getResources().getString( R.string.validcpassword ) );
            return false;
        }
        return true;
    }

    public void getChangePasswordAPI(JsonObject jsonObject) {


        view.showWait();

        Subscription subscription = service.getChangePassword(jsonObject, new Service.GetChangePasswordCallback() {


            @Override
            public void onSuccess(MessageModel cityListResponse) {
                view.removeWait();
                view.getChangePasswordSuccess(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });

        subscriptions.add(subscription);
    }


    public void onStop() {
        subscriptions.unsubscribe();
    }
}
