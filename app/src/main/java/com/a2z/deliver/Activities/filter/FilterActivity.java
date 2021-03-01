package com.a2z.deliver.activities.filter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityFilterBinding;
import com.a2z.deliver.models.FiltersModel;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import butterknife.OnClick;

public class FilterActivity extends BaseApp implements View.OnClickListener {
    ActivityFilterBinding binding;
    FilterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectFilter( this );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_filter );
        binding.setClickListener( this );
        binding.filterHeader.setClickListener( this );
        init();
    }

    private void init() {
        presenter = new FilterPresenter( this,binding );
        binding.filterHeader.tvApplyFilter.setText( R.string.apply );
        binding.filterHeader.tvHeaderText.setText( R.string.filter );
        presenter.setFilters();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.filterHeader.ivLoginBack){
            finish();
        } else if (v == binding.ivFilterSmall || v == binding.ivFilterMedium || v == binding.ivFilterLarge || v == binding.ivFilterXlarge || v == binding.ivFilterHuge || v == binding.ivFilterPet || v == binding.filterHeader.tvApplyFilter) {
            presenter.selectFilters( v );
        }
    }

//    @OnClick({R.id.iv_filter_small, R.id.iv_filter_medium, R.id.iv_filter_large, R.id.iv_filter_xlarge, R.id.iv_filter_huge, R.id.iv_filter_pet})
//    public void onViewClicked(View view) {
//        for (int i=0; i< sizes.length; i++){
//            if (!sizes[i]) {
//                sizes[i] = true;
//            } else {
//                sizes[i] = false;
//            }
//            selectSizes( view, sizes[i] );
//        }
//
//    }
//
//    private void selectSizes(View view, boolean isSelect) {
//
//        if (isSelect && view.getId() == R.id.iv_filter_small) {
//            ivFilterSmall.setImageResource( R.drawable.ic_small_select );
//        } else {
//            ivFilterSmall.setImageResource( R.drawable.ic_small );
//        }
//        if (isSelect && view.getId() == R.id.iv_filter_medium){
//            ivFilterMedium.setImageResource( R.drawable.ic_medium_select );
//        } else {
//            ivFilterMedium.setImageResource( R.drawable.ic_medium );
//        }
//        if (isSelect && view.getId() == R.id.iv_filter_large){
//            ivFilterLarge.setImageResource( R.drawable.ic_large_select );
//        } else {
//            ivFilterLarge.setImageResource( R.drawable.ic_large );
//        }
//        if (isSelect && view.getId() == R.id.iv_filter_xlarge){
//            ivFilterXlarge.setImageResource( R.drawable.ic_xlarge_select );
//        } else {
//            ivFilterXlarge.setImageResource( R.drawable.ic_xlarge );
//        }
//        if (isSelect && view.getId() == R.id.iv_filter_huge){
//            ivFilterHuge.setImageResource( R.drawable.ic_huge_select );
//        } else {
//            ivFilterHuge.setImageResource( R.drawable.ic_huge );
//        }
//        if (isSelect && view.getId() == R.id.iv_filter_pet){
//            ivFilterPet.setImageResource( R.drawable.ic_pet_select );
//        } else {
//            ivFilterPet.setImageResource( R.drawable.ic_pet );
//        }
//    }

}
