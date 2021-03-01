package com.a2z.deliver.activities.inviteFriends;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityInviteFriendsBinding;
import com.a2z.deliver.databinding.ActivityItemSummaryBinding;
import com.a2z.deliver.deps.Deps;
import com.a2z.deliver.utils.CommonUtils;

public class InviteFriendsActivity extends BaseApp implements View.OnClickListener {
   ActivityInviteFriendsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectInviteFriends( this );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_invite_friends );
        binding.setClickListener( this );
        binding.inviteFriendsHeader.setClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvShareInviteLink){
            Toast.makeText( getApplicationContext(), R.string.app_name,Toast.LENGTH_SHORT ).show();
        } else if (v == binding.inviteFriendsHeader.ivLoginBack){
            finish();
        }
    }
}
