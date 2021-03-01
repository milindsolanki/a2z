package com.a2z.deliver.activities.documentVerification;

import android.app.Activity;
import android.view.View;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityDocumentVerificationBinding;

public class DocumentVerificationPresenter {
    Activity activity;
    ActivityDocumentVerificationBinding binding;

    public DocumentVerificationPresenter(Activity activity, ActivityDocumentVerificationBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void verifyDocument(View view) {
        switch (view.getId()) {
            case R.id.rb_driver_verified:
                binding.etDriverlicence.setVisibility( View.GONE );
                break;
            case R.id.rb_driver_unverified:
                binding.etDriverlicence.setVisibility( View.VISIBLE );
                break;
            case R.id.rb_idproof_verified:
                binding.etIdproof.setVisibility( View.GONE );
                break;
            case R.id.rb_idproof_unverified:
                binding.etIdproof.setVisibility( View.VISIBLE );
                break;
            case R.id.rb_policy_verified:
                binding.etInsurancePolicy.setVisibility( View.GONE );
                break;
            case R.id.rb_policy_unverified:
                binding.etInsurancePolicy.setVisibility( View.VISIBLE );
                break;
            case R.id.rb_vehicle_verified:
                binding.etVehicleRegistration.setVisibility( View.GONE );
                break;
            case R.id.rb_vehicle_unverified:
                binding.etVehicleRegistration.setVisibility( View.VISIBLE );
                break;
        }
    }
}
