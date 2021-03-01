package com.a2z.deliver.fragments.onGoingItem;

import android.app.Activity;
import android.util.Log;

import com.a2z.deliver.models.onGoing.OnGoingItemsMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class OnGoingPresenter {
    private final Service service;
    private final OnGoingView view;
    private CompositeSubscription subscriptions;
    Activity activity;


    public OnGoingPresenter(Activity activity, Service service, OnGoingView view) {
        this.service = service;
        this.view = view;
        this.activity = activity;
        this.subscriptions = new CompositeSubscription( );
    }
    public void getOnGoingItem (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getOnGoingItems( jsonObject, new Service.GetOnGoingItemsCallback() {
            @Override
            public void onSuccess(OnGoingItemsMaster onGoingItemsMaster) {
                Log.e( "onsuccess","yes");
                view.removeWait();
                view.getOnGoingItems( onGoingItemsMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                Log.e( "onerroe","network error");
                view.onFailure( networkError.getAppErrorMessage() );
            }
        } );
        subscriptions.add( subscription );
    }
    public void onStop(){
        subscriptions.unsubscribe();
    }
}

