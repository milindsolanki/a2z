package com.a2z.deliver.fragments.toPickUp;

import android.app.Activity;
import com.a2z.deliver.models.toPickUp.ToPickUpMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ToPickUpFragmentPresenter {
    private final Service service;
    private final ToPickUpFragmentView view;
    private CompositeSubscription subscriptions;
    Activity activity;

    public ToPickUpFragmentPresenter(Activity activity, Service service, ToPickUpFragmentView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription(  );
    }

    public void getPickupOrders (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getPickUpOrders( jsonObject, new Service.GetPickupOrderCallback() {
            @Override
            public void onSuccess(ToPickUpMaster toPickUpMaster) {
                view.removeWait();
                view.getPickUpOrderSuccess( toPickUpMaster );
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
