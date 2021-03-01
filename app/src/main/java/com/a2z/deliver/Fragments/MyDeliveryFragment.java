package com.a2z.deliver.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.R;
import com.a2z.deliver.fragments.onTheWay.OnTheWayFragment;
import com.a2z.deliver.fragments.toPickUp.ToPickUpFragment;

import java.util.ArrayList;
import java.util.List;

public class MyDeliveryFragment extends BaseFragmentApp {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Log.e( "TEST", "Delivery Fragment" );
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment( new ToPickUpFragment(), "To Pick-up" );
        adapter.addFragment( new OnTheWayFragment(), "On The way" );
//        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setAdapter( adapter );
//        viewPager.setOffscreenPageLimit( 1 );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_my_delivery, container, false );
        tabLayout = view.findViewById( R.id.tl_mydelivery );
        viewPager = view.findViewById( R.id.vp_mydelivery );
        setupViewPager();
        tabLayout.setupWithViewPager( viewPager );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>(  );
        private final List<String> fragmentTitleList = new ArrayList<>(  );

        public ViewPagerAdapter(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get( position );
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add( fragment );
            fragmentTitleList.add( title );
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get( position );
        }
    }
}
