package com.a2z.deliver.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import com.a2z.deliver.activities.changePassword.ChangePasswordActivity;
import com.a2z.deliver.activities.inviteFriends.InviteFriendsActivity;
import com.a2z.deliver.activities.Profile.ProfileActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.views.CustomTextview;
import com.a2z.deliver.webService.API_Params;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoreFragment extends Fragment {

    Activity activity;

    @BindView(R.id.tv_more_title)
    CustomTextview tvMoreTitle;
    @BindView(R.id.rb_more)
    RatingBar rbMore;
    @BindView(R.id.rv_profile)
    RelativeLayout rvProfile;
    @BindView(R.id.ll_moreinfo_profile)
    LinearLayout llMoreinfoProfile;
    @BindView(R.id.ll_moreinfo_notification)
    LinearLayout llMoreinfoNotification;
    @BindView(R.id.ll_moreinfo_changepwd)
    LinearLayout llMoreinfoChangepwd;
    @BindView(R.id.ll_moreinfo_trackdriver)
    LinearLayout llMoreinfoTrackdriver;
    @BindView(R.id.ll_moreinfo_termsNcondition)
    LinearLayout llMoreinfoTermsNcondition;
    @BindView(R.id.ll_moreinfo_privacy)
    LinearLayout llMoreinfoPrivacy;
    @BindView(R.id.ll_moreinfo_invitefriends)
    LinearLayout llMoreinfoInvitefriends;
    @BindView(R.id.btn_logout)
    LinearLayout btnLogout;
    @BindView(R.id.ll_moreinfo_logout)
    LinearLayout llMoreinfoLogout;
    Unbinder unbinder;
    public static final int RESULT_IMAGE = 501;
    public static final int REQUEST_IMAGE = 401;
    @BindView(R.id.iv_more_profile)
    CircleImageView ivMoreProfile;


    SharedPref sharedPref;
    LoginDetails login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_more, container, false );
        unbinder = ButterKnife.bind( this, view );
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        LinearLayout profile = (LinearLayout) getActivity().findViewById( R.id.ll_moreinfo_profile );
        LinearLayout changepwd = (LinearLayout) getActivity().findViewById( R.id.ll_moreinfo_changepwd );
        LinearLayout invitefriend = (LinearLayout) getActivity().findViewById( R.id.ll_moreinfo_invitefriends );

        sharedPref = SharedPref.getInstance( getActivity() );
        String response = sharedPref.getLoginDetails();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<LoginDetails>() {
        }.getType();
        login = gson.fromJson( response, collectionType );
        String userimage = login.getUserImage();

        tvMoreTitle.setText( login.getFirstName() + " " + login.getLastName() );
        Glide.with( this )
                .load( userimage )
                .into( ivMoreProfile );
        LinearLayout signout = (LinearLayout) getActivity().findViewById( R.id.btn_logout );
        signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                builder.setIcon( R.drawable.ic_signout );
                builder.setTitle( R.string.app_name );
                builder.setMessage( R.string.signout_msg );
                builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( getContext(), MainActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                        SharedPref sharedPref = new SharedPref( getActivity() );
                        sharedPref.resetLoginDetails();
                        sharedPref.deleteAllSharedPrefs();
                    }
                } );
                builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
                builder.show();
            }
        } );

        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ProfileActivity.class );
                startActivityForResult( intent, REQUEST_IMAGE );

            }
        } );

        invitefriend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getContext(), InviteFriendsActivity.class );
                getContext().startActivity( intent );

            }
        } );

        changepwd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), ChangePasswordActivity.class );
                getContext().startActivity( intent );
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == API_Params.RESULT_IMAGE) {
                LoginDetails loginDetails = (LoginDetails) data.getSerializableExtra( API_Params.RESULT );
                if (loginDetails != null) {
                    String updateimage = loginDetails.getUserImage();
                    tvMoreTitle.setText( loginDetails.getFirstName() + " " + loginDetails.getLastName() );
                    //ivMoreProfile.setImageResource( Integer.parseInt( userimage ) );
                    Glide.with( this )
                            .load( updateimage )
                            .into( ivMoreProfile );
                    Log.e( "Image", "img:" + tvMoreTitle );
                }
            }
        }
    }
}
