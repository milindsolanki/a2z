package com.a2z.deliver.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a2z.deliver.Models.Items.ItemDetailMaster;
import com.a2z.deliver.Models.Items.ItemDetails;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Codes;
import com.a2z.deliver.WebService.API_Params;
import com.a2z.deliver.WebService.ApiHandler;
import com.a2z.deliver.WebService.ApiManager;
import com.a2z.deliver.WebService.ApiResponseInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ItemSummaryActivity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.tv_applyFilter)
    CustomTextview tvApplyFilter;
    @BindView(R.id.recyclerview_summary)
    RecyclerView recyclerviewSummary;
    @BindView(R.id.tv_summary_km)
    CustomTextview tvSummaryKm;
    @BindView(R.id.iv_short_itemImage)
    ImageView ivShortItemImage;
    @BindView(R.id.tv_short_itemName)
    CustomTextview tvShortItemName;
    @BindView(R.id.tv_short_pickupAdd)
    CustomTextview tvShortPickupAdd;
    @BindView(R.id.tv_short_deliveryAdd)
    CustomTextview tvShortDeliveryAdd;
    @BindView(R.id.iv_short_map)
    ImageView ivShortMap;
    @BindView(R.id.iv_long_itemSizeImage)
    ImageView ivLongItemSizeImage;
    @BindView(R.id.tv_long_itemSize)
    CustomTextview tvLongItemSize;
    @BindView(R.id.tv_long_itemDescription)
    CustomTextview tvLongItemDescription;
    @BindView(R.id.iv_long_itemImage)
    ImageView ivLongItemImage;
    @BindView(R.id.tv_long_itemName)
    CustomTextview tvLongItemName;
    @BindView(R.id.tv_long_pickupAdd)
    CustomTextview tvLongPickupAdd;
    @BindView(R.id.tv_long_deliveryAdd)
    CustomTextview tvLongDeliveryAdd;
    @BindView(R.id.iv_long_map)
    ImageView ivLongMap;
    @BindView(R.id.tv_long_receiverName)
    CustomTextview tvLongReceiverName;
    @BindView(R.id.tv_long_receiverNumber)
    CustomTextview tvLongReceiverNumber;
    @BindView(R.id.tv_long_pickupTime)
    CustomTextview tvLongPickupTime;
    @BindView(R.id.tv_long_deliveryTime)
    CustomTextview tvLongDeliveryTime;
    @BindView(R.id.tv_long_extraDetails)
    CustomTextview tvLongExtraDetails;
    @BindView(R.id.tv_long_pickupCode)
    CustomTextview tvLongPickupCode;
    @BindView(R.id.tv_long_deliveryCode)
    CustomTextview tvLongDeliveryCode;
    @BindView(R.id.tv_showHideDetails)
    CustomTextview tvShowHideDetails;
    @BindView(R.id.layout_long)
    LinearLayout layoutLong;

    ApiManager apiManager;

    boolean visibleLayout = false;
    @BindView(R.id.layout_short)
    LinearLayout layoutShort;
    SharedPref sharedPref;
    ItemDetails itemDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_summary );
        ButterKnife.bind( this );

        sharedPref = new SharedPref( this );
        String response = sharedPref.getLoginDetails();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<ItemDetails>() {}.getType();
        itemDetails = gson.fromJson( response, collectionType );

        layoutLong.setVisibility( View.GONE );

        apiManager = new ApiManager( this, this );

        initializeApi();

    }

    private void initializeApi() {
        Call<ItemDetailMaster> itemDetailMasterCall = ApiHandler.getApiService().getItemDetailsApi( itemDetailParems() );
        apiManager.makeApiRequest( itemDetailMasterCall, API_Codes.getItemDetails );
    }

    private JsonObject itemDetailParems() {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put( API_Params.orderId, itemDetails.getUserId() );
            jsonObject.put( API_Params.userId, "1" );

            JsonParser parser = new JsonParser();
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_showHideDetails)
    public void onShowClicked() {
        if (!visibleLayout) {
            visibleLayout = true;
            layoutLong.setVisibility( View.VISIBLE );
            layoutShort.setVisibility( View.GONE );
            tvShowHideDetails.setText( "Hide full details" );
            tvShowHideDetails.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_up_arrow,0,0,0 );
        } else {
            visibleLayout = false;
            layoutLong.setVisibility( View.GONE );
            layoutShort.setVisibility( View.VISIBLE );
            tvShowHideDetails.setText( "Show full details" );
            tvShowHideDetails.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_down_arrow,0,0,0 );
        }

    }

    @Override
    public void isError(String errorCode, int apiCode) {
        CommonUtils.makeToast( getApplicationContext(), "Network Error!!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.getItemDetails) {
            ItemDetailMaster itemDetailMaster = (ItemDetailMaster) response;
            if (itemDetailMaster != null) {
                if (itemDetailMaster.getSuccess() == 1) {
                    CommonUtils.makeToast( getApplicationContext(), "Success" );
                    ItemDetails itemDetails = itemDetailMaster.getItemDetails();
                } else if (itemDetailMaster.getSuccess() == 0) {
                    CommonUtils.makeToast( getApplicationContext(), "Failure" );
                }
            }
        }
    }
}
