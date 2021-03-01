package com.a2z.deliver.fragments.more;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.a2z.deliver.R;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.databinding.FragmentMoreBinding;
import com.a2z.deliver.utils.SharedPref;

import static com.a2z.deliver.views.CustomApplication.getContext;

public class MorePresenter {
    Activity activity;
    MoreFragment moreFragment;

    public MorePresenter(MoreFragment moreFragment, FragmentMoreBinding binding) {
        this.activity = activity;
        this.moreFragment = moreFragment;
    }


    public void loadFragment(Fragment fragment, FragmentActivity activity) {
        String backState = fragment.getClass( ).getName( );
        String fragmentTag = backState;

        Log.e( "STATUS:", fragment.getClass( ).getName( ).toString( ) );

        FragmentManager fragmentManager = activity.getSupportFragmentManager( );
        boolean fragmentPopped = fragmentManager.popBackStackImmediate( backState, 0 );

        if (!fragmentPopped && fragmentManager.findFragmentByTag( fragmentTag ) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction( );
            fragmentTransaction.replace( R.id.ll_moreinfo_histroy, fragment, fragmentTag );
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            fragmentTransaction.addToBackStack( backState );
            fragmentTransaction.commit( );
        }
    }
}
