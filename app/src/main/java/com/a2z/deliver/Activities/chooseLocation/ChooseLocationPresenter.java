package com.a2z.deliver.activities.chooseLocation;

import android.app.Activity;

import com.a2z.deliver.models.chooseLocation.AddressMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ChooseLocationPresenter {
    private final Service service;
    private final ChooseLocationView view;
    private CompositeSubscription subscriptions;
    private Activity activity;

    public ChooseLocationPresenter(Activity activity, Service service, ChooseLocationView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getAddressListApi() {
        view.showWait();
        Subscription subscription = service.getAddressList(getAddressListParams(), new Service.GetAddressListCallback() {


            @Override
            public void onSuccess(AddressMaster addressMaster) {
                view.removeWait();
                view.getAddressList(addressMaster);
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



    private JsonObject getAddressListParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.userId, SharedPref.getInstance(activity).getLoginDetailsModel().getUserId());

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }
}
