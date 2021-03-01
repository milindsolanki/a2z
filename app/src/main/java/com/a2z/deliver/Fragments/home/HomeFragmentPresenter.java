package com.a2z.deliver.fragments.home;

import android.app.Activity;

import com.a2z.deliver.models.items.ItemMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.google.gson.JsonObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomeFragmentPresenter {
    private final Service service;
    private final HomeFragmentView view;
    private CompositeSubscription subscriptions;
    Activity activity;

    public HomeFragmentPresenter(Activity activity, Service service, HomeFragmentView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getItemList(JsonObject jsonObject) {
        view.showWait();

        Subscription subscription = service.getItemList(jsonObject, new Service.GetItemListCallback() {
            @Override
            public void onSuccess(ItemMaster itemMaster) {
                view.removeWait();
                view.getItemListSuccess(itemMaster);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.onFailure(networkError.getAppErrorMessage());
            }
        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
