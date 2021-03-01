package com.a2z.deliver.fragments.upComingItem;

import android.app.Activity;
import android.util.Log;

import com.a2z.deliver.models.onGoing.OnGoingItemsMaster;
import com.a2z.deliver.models.upComeingItem.UpComingItemMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class UpComingPresenter {
    private final Service service;
    private final UpComingView view;
    private CompositeSubscription subscriptions;
    Activity activity;


    public UpComingPresenter(Activity activity, Service service, UpComingView view) {
        this.service = service;
        this.view = view;
        this.activity = activity;
        this.subscriptions = new CompositeSubscription( );
    }
    public void getOnGoingItem (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getUpGomingItems( jsonObject, new Service.GetUpComingItemsCallback() {
            @Override
            public void onSuccess(UpComingItemMaster upComingItemMaster) {
                Log.e( "onsuccess","yes");
                view.removeWait();
                view.getUpComingItems( upComingItemMaster );
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

