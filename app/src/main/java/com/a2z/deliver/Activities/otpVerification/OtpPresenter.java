package com.a2z.deliver.activities.otpVerification;

import android.app.Activity;

import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class OtpPresenter {
    private final Service service;
    private final OtpView view;
    private CompositeSubscription subscriptions;
    private Activity activity;

    public OtpPresenter( Activity activity, Service service,OtpView view) {
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.activity = activity;
        this.service = service;
    }

    public void getOtpAPI(JsonObject jsonObject) {
        view.showWait( );
        Subscription subscription = service.getOtpVerificatio( jsonObject, new Service.GetOtpCallback( ) {
            @Override
            public void onSuccess(MessageModel messageModel) {
                view.removeWait( );
                view.getOtpSuccess( messageModel );

            }
            @Override
            public void onError(NetworkError networkError) {
                view.removeWait( );
                view.onFailure( networkError.getAppErrorMessage( ) );
            }

        } );
        subscriptions.add( subscription );
    }

    public void onStop() {
        subscriptions.unsubscribe( );
    }

}
