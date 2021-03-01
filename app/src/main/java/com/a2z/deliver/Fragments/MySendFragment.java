package com.a2z.deliver.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import com.a2z.deliver.R;
import com.a2z.deliver.fragments.onGoingItem.OngoingFragment;
import com.a2z.deliver.fragments.upComingItem.UpcomingFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MySendFragment extends Fragment {
    @BindView(R.id.tl_mysend)
    TabLayout tabLayout;
    @BindView(R.id.vp_mysend)
    ViewPager viewPager;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_send, container, false );
        unbinder = ButterKnife.bind( this, view );
        setupViewPager();
        tabLayout.setupWithViewPager( viewPager );
        return view;
    }

    private void setupViewPager(){
        MySendAdapter mySendAdapter = new MySendAdapter (getChildFragmentManager());
        mySendAdapter.addFragment( new UpcomingFragment(),"Upcoming");
        mySendAdapter.addFragment( new OngoingFragment(), "Ongoing" );
        viewPager.setAdapter( mySendAdapter );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class MySendAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>(  );
        private final List<String> fragmentTitleList = new ArrayList<>(  );

        public MySendAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
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
