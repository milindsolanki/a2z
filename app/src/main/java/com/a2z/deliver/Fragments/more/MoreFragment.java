package com.a2z.deliver.fragments.more;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.activities.changePassword.ChangePasswordActivity;
import com.a2z.deliver.activities.inviteFriends.InviteFriendsActivity;
import com.a2z.deliver.activities.Profile.ProfileActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.databinding.FragmentMoreBinding;
import com.a2z.deliver.fragments.TransectionHistory.TransectionHistoryFragment;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.a2z.deliver.views.CustomApplication.getContext;

public class MoreFragment extends BaseFragmentApp implements View.OnClickListener {

    FragmentMoreBinding binding;
    MorePresenter presenter;
    Activity activity;
    public static final int RESULT_CODE = 501;
    public static final int REQUEST_IMAGE = 401;
    SharedPref sharedPref;
    LoginDetails login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_more, container, false );
        binding.setClickListener( this );
        View view = binding.getRoot( );
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        sharedPref = SharedPref.getInstance( getActivity( ) );
        login = sharedPref.getLoginDetailsModel( );
        binding.tvMoreTitle.setText( login.getFirstName( ) + " " + login.getLastName( ) );
        CommonUtils.setImageUsingGlide( ( Activity ) binding.ivMoreProfile.getContext( ), login.getUserImage( ), binding.ivMoreProfile, null );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == API_Params.RESULT_IMAGE) {
                LoginDetails loginDetails = ( LoginDetails ) data.getSerializableExtra( API_Params.RESULT );
                if (loginDetails != null) {
                    String updateimage = loginDetails.getUserImage( );
                    binding.tvMoreTitle.setText( loginDetails.getFirstName( ) + " " + loginDetails.getLastName( ) );
                    //ivMoreProfile.setImageResource( Integer.parseInt( userimage ) );
                    Glide.with( this )
                            .load( updateimage )
                            .into( binding.ivMoreProfile );
                    Log.e( "Image", "img:" + binding.tvMoreTitle );
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.llMoreinfoProfile) {
            Intent intent = new Intent( getActivity( ), ProfileActivity.class );
            startActivityForResult( intent, REQUEST_IMAGE );
        } else if (v == binding.llMoreinfoNotification) {

        } else if (v == binding.llMoreinfoChangepwd) {
            Intent intent = new Intent( getContext( ), ChangePasswordActivity.class );
            startActivityForResult( intent, RESULT_CODE );
        } else if (v == binding.llMoreinfoHistroy) {
            presenter = new MorePresenter( this, binding );
            presenter.loadFragment( new TransectionHistoryFragment(), getActivity( ) );

        } else if (v == binding.llMoreinfoTrackdriver) {

        } else if (v == binding.llMoreinfoTermsNcondition) {

        } else if (v == binding.llMoreinfoPrivacy) {

        } else if (v == binding.llMoreinfoInvitefriends) {
            Intent intent = new Intent( getContext( ), InviteFriendsActivity.class );
            startActivityForResult( intent, RESULT_CODE );
        }else if(v==binding.llMoreinfoLogout){
            getlogout( getResources().getString( R.string.app_name ),getResources().getString( R.string.signout_msg ));
        }
    }

    class MoreAdapter extends FragmentPagerAdapter {
        private final List <Fragment> fragmentList = new ArrayList <>( );
        private final List <String> fragmentTitleList = new ArrayList <>( );

        public MoreAdapter(FragmentManager supportFragmentManager) {
            super( supportFragmentManager );
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get( position );
        }

        @Override
        public int getCount() {
            return fragmentList.size( );
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add( fragment );
            fragmentTitleList.add( title );
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get( position );
        }
    }
    public void getlogout(String sTitle, String sMessage) {

        final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setIcon( R.drawable.ic_signout );
        builder.setTitle( sTitle );
        builder.setMessage( sMessage );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent( getActivity(), MainActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                getActivity().startActivity( intent );
                SharedPref sharedPref = new SharedPref( getActivity() );
                sharedPref.resetLoginDetails( );
                sharedPref.deleteAllSharedPrefs( );

            }
        } );
        builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel( );
            }
        } );
        builder.show( );
    }

}


