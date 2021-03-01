package com.a2z.deliver.activities.home;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityHomeBinding;

/**
 * Created by Aprod LLC. on 7/26/2018.
 */
public class HomePresenter {
    Activity activity;
    ActivityHomeBinding binding;
    public HomePresenter(Activity activity, ActivityHomeBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void loadFragment(Fragment fragment, FragmentActivity activity) {
        String backState = fragment.getClass().getName();
        String fragmentTag = backState;

        Log.e( "STATUS:", fragment.getClass().getName().toString() );

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate( backState, 0 );

        if (!fragmentPopped && fragmentManager.findFragmentByTag( fragmentTag ) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.home_framelayout, fragment, fragmentTag );
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            fragmentTransaction.addToBackStack( backState );
            fragmentTransaction.commit();
        }
    }

    public void setHomeUI() {
        binding.homeHeader.ivHomeMap.setImageResource( R.drawable.ic_map );
        binding.ivBottomHome.setImageResource( R.drawable.ic_home_colorprimary );
        binding.tvBottomHome.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );



        binding.ivBottomMydelivery.setImageResource( R.drawable.ic_steering_wheel_gray );
        binding.tvBottomMydelivery.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMysend.setImageResource( R.drawable.ic_send_gray );
        binding.tvBottomMysend.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMore.setImageResource( R.drawable.ic_more );
        binding.tvBottomMore.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );

        binding.homeHeader.ivHomeFilter.setVisibility( View.VISIBLE );
        binding.homeHeader.ivHomeMap.setVisibility( View.VISIBLE );
        binding.homeHeader.tvHeaderText.setText( R.string.pickyourdelivery );
    }

    public void setMyDeliveryUI(){
        binding.ivBottomMydelivery.setImageResource( R.drawable.ic_steering_wheel_colorprimary );
        binding.tvBottomMydelivery.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
        binding.ivBottomHome.setImageResource( R.drawable.ic_home_gray );
        binding.tvBottomHome.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMysend.setImageResource( R.drawable.ic_send_gray );
        binding.tvBottomMysend.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMore.setImageResource( R.drawable.ic_more );
        binding.tvBottomMore.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );

        binding.homeHeader.ivHomeFilter.setVisibility( View.GONE );
        binding.homeHeader.ivHomeMap.setVisibility( View.GONE );
        binding.homeHeader.tvHeaderText.setText( R.string.myDelivery );
    }

    public void setMySendUI(){
        binding.ivBottomMysend.setImageResource( R.drawable.ic_send_blue );
        binding.tvBottomMysend.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
        binding.ivBottomHome.setImageResource( R.drawable.ic_home_gray );
        binding.tvBottomHome.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMydelivery.setImageResource( R.drawable.ic_steering_wheel_gray );
        binding.tvBottomMydelivery.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMore.setImageResource( R.drawable.ic_more );
        binding.tvBottomMore.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );

        binding.homeHeader.ivHomeFilter.setVisibility( View.GONE );
        binding.homeHeader.ivHomeMap.setVisibility( View.GONE );
        binding.homeHeader.tvHeaderText.setText( R.string.mySend );
    }

    public void setMoreUI(){
        binding.ivBottomMore.setImageResource( R.drawable.ic_more_color );
        binding.tvBottomMore.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
        binding.ivBottomHome.setImageResource( R.drawable.ic_home_gray );
        binding.tvBottomHome.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMydelivery.setImageResource( R.drawable.ic_steering_wheel_gray );
        binding.tvBottomMydelivery.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
        binding.ivBottomMysend.setImageResource( R.drawable.ic_send_gray );
        binding.tvBottomMysend.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );

        binding.homeHeader.ivHomeFilter.setVisibility( View.GONE );
        binding.homeHeader.ivHomeMap.setVisibility( View.GONE );
        binding.homeHeader.tvHeaderText.setText( R.string.moreinfo );
    }
}
