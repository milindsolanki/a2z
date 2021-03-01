package com.a2z.deliver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2z.deliver.Models.FiltersModel;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.tv_applyFilter)
    CustomTextview tvApplyFilter;
    @BindView(R.id.tv_origin)
    LinearLayout tvOrigin;
    @BindView(R.id.tv_destination)
    LinearLayout tvDestination;
    @BindView(R.id.iv_filter_small)
    ImageView ivFilterSmall;
    @BindView(R.id.iv_filter_medium)
    ImageView ivFilterMedium;
    @BindView(R.id.iv_filter_large)
    ImageView ivFilterLarge;
    @BindView(R.id.iv_filter_xlarge)
    ImageView ivFilterXlarge;
    @BindView(R.id.iv_filter_huge)
    ImageView ivFilterHuge;
    @BindView(R.id.iv_filter_pet)
    ImageView ivFilterPet;
    @BindView(R.id.tv_small)
    CustomTextview tvSmall;
    @BindView(R.id.tv_medium)
    CustomTextview tvMedium;
    @BindView(R.id.tv_large)
    CustomTextview tvLarge;
    @BindView(R.id.tv_xlarge)
    CustomTextview tvXlarge;
    @BindView(R.id.tv_huge)
    CustomTextview tvHuge;
    @BindView(R.id.tv_pet)
    CustomTextview tvPet;

    boolean isSmall = false;
    boolean isMedium = false;
    boolean isLarge = false;
    boolean isXlarge = false;
    boolean isHuge = false;
    boolean isPet = false;
    int isfilteraltered;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_filter );
        ButterKnife.bind( this );

        sharedPref = new SharedPref( this );
        tvApplyFilter.setText( R.string.apply );
        tvHeaderText.setText( R.string.filter );
        setFilters();

    }

    private void setFilters() {
        String response = sharedPref.getFilters();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<FiltersModel>() {}.getType();
        if (!CommonUtils.isTrimEmpty( response )) {
            FiltersModel filtersModel = gson.fromJson( response, collectionType );
            isfilteraltered = filtersModel.getIsFilter();
            String cat[] = filtersModel.getCategory().split( "," );
            if (cat != null && cat.length > 0) {
                for (int i = 0; i < cat.length; i++) {
                    if (cat[i].equals( String.valueOf(  API_Params.SMALL_CATEGORY))){
                        isSmall = true;
                        ivFilterSmall.setImageResource( R.drawable.ic_small_select );
                        tvSmall.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.MEDIUM_CATEGORY ))){
                        isMedium = true;
                        ivFilterMedium.setImageResource( R.drawable.ic_medium_select );
                        tvMedium.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.LARGE_CATEGORY ))){
                        isLarge = true;
                        ivFilterLarge.setImageResource( R.drawable.ic_large_select );
                        tvLarge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.XLARGE_CATEGORY ))){
                        isXlarge = true;
                        ivFilterXlarge.setImageResource( R.drawable.ic_xlarge_select );
                        tvXlarge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.HUGE_CATEGORY ) )){
                        isHuge = true;
                        ivFilterHuge.setImageResource( R.drawable.ic_huge_select );
                        tvHuge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.PET_CATEGORY ))){
                        isPet = true;
                        ivFilterPet.setImageResource( R.drawable.ic_pet_select );
                        tvPet.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                    }
                }
            }
        }

    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_applyFilter)
    public void onFilterClicked() {
        tvApplyFilter.setTextColor( getResources().getColor( R.color.colorPrimary ) );

        Log.e( "IS SMALL SELECTED: ", String.valueOf( isSmall ) );
        Log.e( "IS MEDIUM SELECTED: ", String.valueOf( isMedium ) );
        Log.e( "IS LARGE SELECTED: ", String.valueOf( isLarge ) );
        Log.e( "IS HUGE SELECTED: ", String.valueOf( isHuge ) );
        Log.e( "IS XLARGE SELECTED: ", String.valueOf( isXlarge ) );
        Log.e( "IS PET SELECTED: ", String.valueOf( isPet ) );

        String categories = "0";
        if (isSmall == false && isMedium == false && isLarge == false && isXlarge == false && isHuge == false && isPet == false) {
            isfilteraltered = 0;
        } else {
            isfilteraltered = 1;
            categories = "";
            if (isSmall) {
                if (categories.equals( "" )) {
                    categories += API_Params.SMALL_CATEGORY;
                } else {
                    categories += "," + API_Params.SMALL_CATEGORY;
                }
            }
            if (isMedium) {
                if (categories.equals( "" )) {
                    categories += API_Params.MEDIUM_CATEGORY;
                } else {
                    categories += "," + API_Params.MEDIUM_CATEGORY;
                }
            }
            if (isLarge){
                if (categories.equals( "" )) {
                    categories += API_Params.LARGE_CATEGORY;
                } else {
                    categories += "," + API_Params.LARGE_CATEGORY;
                }
            }
            if (isXlarge){
                if (categories.equals( "" )) {
                    categories += API_Params.XLARGE_CATEGORY;
                } else {
                    categories += "," + API_Params.XLARGE_CATEGORY;
                }
            }
            if (isHuge){
                if (categories.equals( "" )) {
                    categories += API_Params.HUGE_CATEGORY;
                } else {
                    categories += "," + API_Params.HUGE_CATEGORY;
                }
            }
            if (isPet){
                if (categories.equals( "" )) {
                    categories += API_Params.PET_CATEGORY;
                } else {
                    categories += "," + API_Params.PET_CATEGORY;
                }
            }
        }
        Log.e( "IS Filter ", "value" + isfilteraltered );
        Log.e( "FILTER ",  categories );

        JsonObject gson = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject(  );
            jsonObject.put( API_Params.isFilter, isfilteraltered);
            jsonObject.put( API_Params.category, categories);

            JsonParser jsonParser = new JsonParser();
            gson = (JsonObject) jsonParser.parse( jsonObject.toString() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedPref.storeFilters( gson.toString() );
    }

    @OnClick(R.id.iv_filter_small)
    public void onSmallClicked() {
        if (isSmall) {
            isSmall = false;
            ivFilterSmall.setImageResource( R.drawable.ic_small );
            tvSmall.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isSmall = true;
            ivFilterSmall.setImageResource( R.drawable.ic_small_select );
            tvSmall.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        }
    }

    @OnClick(R.id.iv_filter_medium)
    public void onMediumClicked() {
        if (isMedium) {
            isMedium = false;
            ivFilterMedium.setImageResource( R.drawable.ic_medium );
            tvMedium.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isMedium = true;
            ivFilterMedium.setImageResource( R.drawable.ic_medium_select );
            tvMedium.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        }
    }

    @OnClick(R.id.iv_filter_large)
    public void onLargeClicked() {
        if (isLarge) {
            isLarge = false;
            ivFilterLarge.setImageResource( R.drawable.ic_large );
            tvLarge.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isLarge = true;
            ivFilterLarge.setImageResource( R.drawable.ic_large_select );
            tvLarge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        }
    }

    @OnClick(R.id.iv_filter_xlarge)
    public void onXlargeClicked() {
        if (isXlarge) {
            isXlarge = false;
            ivFilterXlarge.setImageResource( R.drawable.ic_xlarge );
            tvXlarge.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isXlarge = true;
            ivFilterXlarge.setImageResource( R.drawable.ic_xlarge_select );
            tvXlarge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        }
    }

    @OnClick(R.id.iv_filter_huge)
    public void onHugeClicked() {
        if (isHuge) {
            isHuge = false;
            ivFilterHuge.setImageResource( R.drawable.ic_huge );
            tvHuge.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isHuge = true;
            ivFilterHuge.setImageResource( R.drawable.ic_huge_select );
            tvHuge.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        }
    }

    @OnClick(R.id.iv_filter_pet)
    public void onPetClicked() {
        if (isPet) {
            isPet = false;
            ivFilterPet.setImageResource( R.drawable.ic_pet );
            tvPet.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        } else {
            isPet = true;
            ivFilterPet.setImageResource( R.drawable.ic_pet_select );
            tvPet.setTextColor( getResources().getColor( R.color.colorPrimary ) );
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
