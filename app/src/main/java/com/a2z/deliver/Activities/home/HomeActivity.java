package com.a2z.deliver.Activities.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.a2z.deliver.activities.filter.FilterActivity;
import com.a2z.deliver.activities.sendItem.SendActivity;
import com.a2z.deliver.BaseApp;
import com.a2z.deliver.fragments.home.HomeFragment;
import com.a2z.deliver.fragments.homeMap.HomeMapFragment;
import com.a2z.deliver.fragments.more.MoreFragment;
import com.a2z.deliver.fragments.MyDeliveryFragment;
import com.a2z.deliver.fragments.MySendFragment;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityHomeBinding;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;

public class HomeActivity extends BaseApp implements View.OnClickListener {
    boolean isMap = true;
    com.a2z.deliver.activities.home.HomePresenter homePresenter;
    ActivityHomeBinding binding;
    SharedPref sharedPref;
    public static final int REQUEST_FILTER = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectHome(this);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_home );
        binding.setViewListener(this);
        binding.homeHeader.setViewListener(this);
        init();
    }

    public void init(){
        homePresenter = new com.a2z.deliver.activities.home.HomePresenter(this, binding);
        homePresenter.loadFragment(new HomeFragment(), this);
        sharedPref = SharedPref.getInstance( this );
    }

    public void onBackPressed() {
        String isLogin = sharedPref.getLoginDetails();
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        Fragment deliveryFragment = getSupportFragmentManager().findFragmentByTag(MyDeliveryFragment.class.getName());
        Fragment mySendFragment = getSupportFragmentManager().findFragmentByTag(MySendFragment.class.getName());
        Fragment moreFragment = getSupportFragmentManager().findFragmentByTag(MoreFragment.class.getName());
        Log.e( "TAG", "" + getSupportFragmentManager().getBackStackEntryCount() );
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (!CommonUtils.isEmpty( isLogin )){
                ActivityCompat.finishAffinity(this);
            }
        } else {
            super.onBackPressed();
            if (deliveryFragment != null && deliveryFragment.isVisible()) {
                homePresenter.setMyDeliveryUI();
            } else if (mySendFragment != null && mySendFragment.isVisible()) {
                homePresenter.setMySendUI();
            } else if (moreFragment != null && moreFragment.isVisible()) {
                homePresenter.setMoreUI();
            } else if (homeFragment != null && homeFragment.isVisible()) {
                homePresenter.setHomeUI();
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v == binding.layoutHome) {
            homePresenter.loadFragment(new HomeFragment(), this);
            homePresenter.setHomeUI();
        } else if(v == binding.layoutMydelivery) {
            homePresenter.loadFragment(new MyDeliveryFragment(), this);
            homePresenter.setMyDeliveryUI();
        } else if(v == binding.layoutMysend) {
            homePresenter.loadFragment(new MySendFragment(), this);
            homePresenter.setMySendUI();
        } else if(v == binding.layoutMore) {
            homePresenter.loadFragment(new MoreFragment(), this);
            homePresenter.setMoreUI();
        } else if(v == binding.ibSend){
            Intent intent = new Intent( HomeActivity.this, SendActivity.class );
            startActivity( intent );
        } else if(v == binding.homeHeader.ivHomeFilter){
            Intent intent = new Intent( HomeActivity.this, FilterActivity.class );
            startActivityForResult( intent, REQUEST_FILTER );
        } else if(v == binding.homeHeader.ivHomeMap){
            if (isMap) {
                isMap = false;
                homePresenter.loadFragment(new HomeMapFragment(), this);
                binding.homeHeader.ivHomeMap.setImageResource( R.drawable.ic_list );
            } else {
                isMap = true;
                homePresenter.loadFragment(new HomeFragment(), this);
                binding.homeHeader.ivHomeMap.setImageResource( R.drawable.ic_map );
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILTER && resultCode == RESULT_OK) {
            HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            fragment.getFilterItemListApi();
        }
    }
}
