package com.a2z.deliver.activities.pickupDetails;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityPickUpDetailsBinding;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.webService.API_Params;

import rx.subscriptions.CompositeSubscription;

public class PickUpDetailsPresenter {
    private final Service service;
    private PickUpDetailsView view;
    private CompositeSubscription subscriptions;
    private ActivityPickUpDetailsBinding binding;
    private Activity activity;

    public PickUpDetailsPresenter(Service service, PickUpDetailsView view, Activity activity, ActivityPickUpDetailsBinding binding) {
        this.service = service;
        this.view = view;
        this.binding = binding;
        this.subscriptions = new CompositeSubscription(  );
        this.activity = activity;
    }

    public void codeView(){
        binding.tvCode1.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.tvCode1.getText().toString().length() == 1){
                    binding.tvCode2.requestFocus();
                }
//                if (!CommonUtils.isEmpty( binding.tvCode1.getText().toString() )  || !CommonUtils.isEmpty( binding.tvCode2.getText().toString() ) ||
//                        !CommonUtils.isEmpty( binding.tvCode3.getText().toString()) || CommonUtils.isEmpty( binding.tvCode4.getText().toString() ) ){
//                    setStartJourneyEnable( false );
//                } else {
//                    setStartJourneyEnable( true );
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvCode1.setInputType( InputType.TYPE_CLASS_NUMBER );
            }
        } );
        binding.tvCode2.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.tvCode2.getText().toString().length() == 1){
                    binding.tvCode3.requestFocus();
                }
                if (binding.tvCode2.getText().toString().length() == 0){
                    if (binding.tvCode1.getText().toString().length() == 1){
                        binding.tvCode1.requestFocus();
                    }
                }
//                if (!CommonUtils.isEmpty( binding.tvCode1.getText().toString() )  || !CommonUtils.isEmpty( binding.tvCode2.getText().toString() ) ||
//                        !CommonUtils.isEmpty( binding.tvCode3.getText().toString()) || CommonUtils.isEmpty( binding.tvCode4.getText().toString() ) ){
//                    setStartJourneyEnable( false );
//                } else {
//                    setStartJourneyEnable( true );
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvCode2.setInputType( InputType.TYPE_CLASS_NUMBER );
            }
        } );
        binding.tvCode3.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.tvCode3.getText().toString().length() == 1){
                    binding.tvCode4.requestFocus();
                }
                if (binding.tvCode3.getText().toString().length() == 0){
                    if (binding.tvCode2.getText().toString().length() == 1){
                        binding.tvCode2.requestFocus();
                    }
                }
//                if (!CommonUtils.isEmpty( binding.tvCode1.getText().toString() )  || !CommonUtils.isEmpty( binding.tvCode2.getText().toString() ) ||
//                        !CommonUtils.isEmpty( binding.tvCode3.getText().toString()) || CommonUtils.isEmpty( binding.tvCode4.getText().toString() ) ){
//                    setStartJourneyEnable( false );
//                } else {
//                    setStartJourneyEnable( true );
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvCode3.setInputType( InputType.TYPE_CLASS_NUMBER );
            }
        } );
        binding.tvCode4.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.tvCode4.getText().toString().length() == 1){

                }
                if (binding.tvCode4.getText().toString().length() == 0){
                    if (binding.tvCode3.getText().toString().length() == 1){
                        binding.tvCode3.requestFocus();
                    }
                }
//                if (!CommonUtils.isEmpty( binding.tvCode1.getText().toString() )  || !CommonUtils.isEmpty( binding.tvCode2.getText().toString() ) ||
//                        !CommonUtils.isEmpty( binding.tvCode3.getText().toString()) || CommonUtils.isEmpty( binding.tvCode4.getText().toString() ) ){
//                    setStartJourneyEnable( false );
//                } else {
//                    setStartJourneyEnable( true );
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvCode4.setInputType( InputType.TYPE_CLASS_NUMBER );
            }
        } );
    }

    public void checkItemType(String itemType){
        if (itemType.equalsIgnoreCase( API_Params.SMALL )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_small_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.small_text );
        } else if (itemType.equalsIgnoreCase( API_Params.MEDIUM )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_medium_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.medium_text );
        } else if (itemType.equalsIgnoreCase( API_Params.LARGE )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_large_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.large_text );
        } else if (itemType.equalsIgnoreCase( API_Params.XLARGE )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_xlarge_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.xlarge_text );
        } else if (itemType.equalsIgnoreCase( API_Params.HUGE )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_huge_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.huge_text );
        } else if (itemType.equalsIgnoreCase( API_Params.PET )) {
            binding.layoutDetails.ivLongItemSizeImage.setImageResource( R.drawable.ic_pet_select );
            binding.layoutDetails.tvLongItemDescription.setText( R.string.pet_text );
        } else {
            Toast.makeText( activity, R.string.itemtypenotfound ,Toast.LENGTH_SHORT ).show();
        }
    }
}
