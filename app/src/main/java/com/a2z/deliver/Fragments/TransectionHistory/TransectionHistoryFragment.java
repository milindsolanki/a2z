package com.a2z.deliver.fragments.TransectionHistory;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.FragmentTransectionHistoryBinding;
import com.a2z.deliver.fragments.DriverHistoryFragment;
import com.a2z.deliver.fragments.SenderHistoryFragment;
import com.a2z.deliver.fragments.onGoingItem.OngoingFragment;
import com.a2z.deliver.fragments.upComingItem.UpcomingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransectionHistoryFragment extends BaseFragmentApp {
 FragmentTransectionHistoryBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate( inflater,R.layout.fragment_transection_history, container, false  );
        binding.setClickListener( ( View.OnClickListener ) getActivity() );
        binding.tbHistory.setupWithViewPager( binding.vpHistory );
        View view=binding.getRoot();
        //setupViewPager();
        return view;       
        
    }

    private void setupViewPager() {
        HistoryAdapter historyAdapter=new HistoryAdapter( getChildFragmentManager() );
        historyAdapter.addFragment( new DriverHistoryFragment(),"Driver" );
        historyAdapter.addFragment( new SenderHistoryFragment(),"Sender" );
        binding.vpHistory.setAdapter( historyAdapter );
    }

    class HistoryAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>(  );
        private final List<String> fragmentTitleList = new ArrayList<>(  );

        public HistoryAdapter(FragmentManager supportFragmentManager) {
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
