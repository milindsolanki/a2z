package com.a2z.deliver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2z.deliver.Fragments.HomeFragment;
import com.a2z.deliver.Fragments.HomeMapFragment;
import com.a2z.deliver.Fragments.MoreFragment;
import com.a2z.deliver.Fragments.MyDeliveryFragment;
import com.a2z.deliver.Fragments.MySendFragment;
import com.a2z.deliver.R;
import com.a2z.deliver.Views.CustomTextview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.action_bar)
    AppBarLayout actionBar;
    @BindView(R.id.home_framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.rl_bottom)
    LinearLayout rlBottom;
    @BindView(R.id.ib_send)
    ImageButton ibSend;
    @BindView(R.id.iv_home_filter)
    ImageView ivHomeFilter;
    @BindView(R.id.iv_home_map)
    ImageView ivHomeMap;
    @BindView(R.id.layout_home)
    LinearLayout layoutHome;
    @BindView(R.id.layout_mydelivery)
    LinearLayout layoutMydelivery;
    @BindView(R.id.layout_mysend)
    LinearLayout layoutMysend;
    @BindView(R.id.layout_more)
    LinearLayout layoutMore;
    @BindView(R.id.iv_bottom_home)
    ImageView ivBottomHome;
    @BindView(R.id.tv_bottom_home)
    TextView tvBottomHome;
    @BindView(R.id.iv_bottom_mydelivery)
    ImageView ivBottomMydelivery;
    @BindView(R.id.tv_bottom_mydelivery)
    TextView tvBottomMydelivery;
    @BindView(R.id.iv_bottom_mysend)
    ImageView ivBottomMysend;
    @BindView(R.id.tv_bottom_mysend)
    TextView tvBottomMysend;
    @BindView(R.id.iv_bottom_more)
    ImageView ivBottomMore;
    @BindView(R.id.tv_bottom_more)
    TextView tvBottomMore;
    private String homeFrag = "com.a2z.deliver.Fragments.HomeFragment";
    private String deliveryFrag = "com.a2z.deliver.Fragments.MyDeliveryFragment";
    private String mySendFrag = "com.a2z.deliver.Fragments.MySendFragment";

    boolean isMap = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        ButterKnife.bind( this );

        tvHeaderText.setText( R.string.pickyourdelivery );

        loadFragment( new HomeFragment() );
        ivBottomHome.setImageResource( R.drawable.ic_home_colorprimary );
        tvBottomHome.setTextColor( getResources().getColor( R.color.colorPrimary ) );
    }

    private void loadFragment(Fragment fragment) {
        String backState = fragment.getClass().getName();
        String fragmentTag = backState;

        Log.e( "STATUS:", fragment.getClass().getName().toString() );

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate( backState, 0 );

        if (!fragmentPopped && fragmentManager.findFragmentByTag( fragmentTag ) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.home_framelayout, fragment, fragmentTag );
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            fragmentTransaction.addToBackStack( backState );
            fragmentTransaction.commit();
        }
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.layout_home)
    public void onHomeClicked() {
        loadFragment( new HomeFragment() );
    }

    @OnClick(R.id.layout_mydelivery)
    public void onMyDeliveryClicked() {
        loadFragment( new MyDeliveryFragment() );
    }

    @OnClick(R.id.layout_mysend)
    public void onMySendClicked() {
        loadFragment( new MySendFragment() );
    }

    @OnClick(R.id.layout_more)
    public void onMoreClicked() {
        loadFragment( new MoreFragment() );
    }

    @OnClick(R.id.iv_home_map)
    public void onMapClicked() {
        if (isMap) {
            isMap = false;
            loadFragment( new HomeMapFragment() );
        } else {
            isMap = true;
            loadFragment( new HomeFragment() );
        }

    }

    @OnClick(R.id.ib_send)
    public void onSendClicked() {
        Intent intent = new Intent( HomeActivity.this, SendActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.iv_home_filter)
    public void onFilterClicked() {
        Intent intent = new Intent( HomeActivity.this, FilterActivity.class );
        startActivity( intent );
    }

}
