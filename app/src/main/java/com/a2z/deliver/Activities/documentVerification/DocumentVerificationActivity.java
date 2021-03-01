package com.a2z.deliver.activities.documentVerification;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityDocumentVerificationBinding;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;

public class DocumentVerificationActivity extends BaseApp implements View.OnClickListener {

    ActivityDocumentVerificationBinding binding;
    DocumentVerificationPresenter presenter;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_document_verification );
        binding.setClickListener( this );

        init();
    }

    public void init() {
        presenter = new DocumentVerificationPresenter( this, binding );

        binding.layoutHeader.tvHeaderText.setText( "Document Verification" );
    }

    @Override
    public void onClick(View v) {
        if (v == binding.rbDriverVerified || v == binding.rbDriverUnverified || v == binding.rbIdproofVerified || v == binding.rbIdproofUnverified ||
                v == binding.rbPolicyVerified || v == binding.rbPolicyUnverified || v == binding.rbVehicleVerified || v == binding.rbVehicleUnverified){
            presenter.verifyDocument( v );

        }
    }
}
