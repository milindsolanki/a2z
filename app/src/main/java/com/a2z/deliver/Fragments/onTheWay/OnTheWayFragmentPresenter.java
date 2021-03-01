package com.a2z.deliver.fragments.onTheWay;

import android.app.Activity;

import com.a2z.deliver.models.onTheWay.OnTheWayMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class OnTheWayFragmentPresenter {
    private final Service service;
    private final OnTheWayFragmentView view;
    private CompositeSubscription subscriptions;
    Activity activity;

    public OnTheWayFragmentPresenter(Activity activity, Service service, OnTheWayFragmentView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getOnTheWayOrders(JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getOnTheWayOrders( jsonObject, new Service.GetOnTheWayOrderCallback() {
            @Override
            public void onSuccess(OnTheWayMaster onTheWayMaster) {
                view.removeWait();
                view.getOnTheWayOrdersSuccess( onTheWayMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure( networkError.getAppErrorMessage() );
            }
        } );
        subscriptions.add( subscription );
    }
    public void onStop(){
        subscriptions.unsubscribe();
    }
}
