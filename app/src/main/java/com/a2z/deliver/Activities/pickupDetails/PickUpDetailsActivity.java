package com.a2z.deliver.activities.pickupDetails;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityPickUpDetailsBinding;
import com.a2z.deliver.models.onTheWay.OnTheWayDetails;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;

import javax.inject.Inject;

public class PickUpDetailsActivity extends BaseApp implements View.OnClickListener, PickUpDetailsView {

    ActivityPickUpDetailsBinding binding;
    PickUpDetailsPresenter presenter;
    @Inject
    public Service service;
    private Activity activity;

    boolean visibleLayout = false;
    String OnTheWayModel = "OnTheWayModel";
    int REQUEST_CODE = 112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectPickupDetails( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_pick_up_details );
        binding.setClickListener( this );
        binding.layoutDetails.setClickListener( this );
        binding.layoutHeader.setClickListener( this );

        init();
        setData();
    }

    public void init() {
        activity =this;
        binding.layoutDetails.layoutDetail.setVisibility( View.GONE );
        binding.layoutHeader.tvHeaderText.setText( R.string.ontheway );
        presenter = new PickUpDetailsPresenter( service, this, this, binding );
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvHideunhideDetails){
            if (!visibleLayout){
                visibleLayout = true;
                binding.layoutDetails.layoutDetail.setVisibility( View.VISIBLE );
                binding.tvHideunhideDetails.setText( R.string.hidefulldetails );
                binding.tvHideunhideDetails.setCompoundDrawablesWithIntrinsicBounds( 0,0,R.drawable.ic_up_arrow,0 );
            } else {
                visibleLayout = false;
                binding.layoutDetails.layoutDetail.setVisibility( View.GONE );
                binding.tvHideunhideDetails.setText( R.string.showfulldetails );
                binding.tvHideunhideDetails.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_down_arrow, 0 );
            }
        } else if (v == binding.btnRoutetopickup){
//            CommonUtils.makeToast( getApplicationContext(), "Test" );
        } else if (v == binding.layoutHeader.ivLoginBack){
            finish();
        }
    }

    public void setData() {
        OnTheWayDetails details = (OnTheWayDetails) getIntent().getSerializableExtra( OnTheWayModel );
        binding.layoutDetails.setItem( details );
        presenter.codeView();
        String itemType = "";
        if (!CommonUtils.isEmpty( details.getItemType() )) {
            itemType = details.getItemType();
        }
        presenter.checkItemType( itemType );
        String whoWillPass = "";
        if (!CommonUtils.isEmpty( details.getWhoWillPass() )) {
            whoWillPass = details.getWhoWillPass();
        }
        if (whoWillPass.equalsIgnoreCase( "1" )) {
            binding.layoutDetails.tvPickupName.setText( "I will pass" );
            binding.layoutDetails.tvPickupPhone.setVisibility( View.GONE );
            binding.layoutDetails.tvPickupEmail.setVisibility( View.GONE );
        } else if (whoWillPass.equalsIgnoreCase( "2" )) {
            binding.layoutDetails.tvPickupName.setText( details.getPassName() );
            binding.layoutDetails.tvPickupPhone.setText( details.getPassMobile() );
            binding.layoutDetails.tvPickupEmail.setText( details.getPassEmail() );
        }

        if (details.getWhoWillMeet().toString().equalsIgnoreCase( "1" )) {
            binding.layoutDetails.tvDeliverName.setText( "I will meet" );
            binding.layoutDetails.tvDeliverPhone.setVisibility( View.GONE );
            binding.layoutDetails.tvDeliverEmail.setVisibility( View.GONE );
        } else if (details.getWhoWillMeet().toString().equalsIgnoreCase( "2" )) {
            binding.layoutDetails.tvDeliverName.setText( details.getMeetName() );
            binding.layoutDetails.tvDeliverPhone.setText( details.getMeetMobile() );
            binding.layoutDetails.tvDeliverEmail.setText( details.getMeetEmail() );
        }
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getPickupDetailsSuccess() {

    }
}
