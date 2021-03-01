package com.a2z.deliver.activities.filter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

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

import rx.subscriptions.CompositeSubscription;

public class FilterPresenter {
    private CompositeSubscription subscriptions;
    private Activity activity;
    private ActivityFilterBinding binding;
    boolean isSmall = false;
    boolean isMedium = false;
    boolean isLarge = false;
    boolean isXlarge = false;
    boolean isHuge = false;
    boolean isPet = false;
    String isfilteraltered;
    SharedPref sharedPref;

    public FilterPresenter(Activity activity, ActivityFilterBinding binding) {
        this.subscriptions = new CompositeSubscription();
        this.activity = activity;
        this.binding = binding;
        sharedPref = SharedPref.getInstance( activity );
    }

    public void selectFilters(View v) {
        if (v == binding.ivFilterSmall){
            if (isSmall) {
                isSmall = false;
                binding.ivFilterSmall.setImageResource( R.drawable.ic_small );
                binding.tvSmall.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isSmall = true;
                binding.ivFilterSmall.setImageResource( R.drawable.ic_small_select );
                binding.tvSmall.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.ivFilterMedium){
            if (isMedium) {
                isMedium = false;
                binding.ivFilterMedium.setImageResource( R.drawable.ic_medium );
                binding.tvMedium.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isMedium = true;
                binding.ivFilterMedium.setImageResource( R.drawable.ic_medium_select );
                binding.tvMedium.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.ivFilterLarge){
            if (isLarge) {
                isLarge = false;
                binding.ivFilterLarge.setImageResource( R.drawable.ic_large );
                binding.tvLarge.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isLarge = true;
                binding.ivFilterLarge.setImageResource( R.drawable.ic_large_select );
                binding.tvLarge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.ivFilterXlarge){
            if (isXlarge) {
                isXlarge = false;
                binding.ivFilterXlarge.setImageResource( R.drawable.ic_xlarge );
                binding.tvXlarge.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isXlarge = true;
                binding.ivFilterXlarge.setImageResource( R.drawable.ic_xlarge_select );
                binding.tvXlarge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.ivFilterHuge){
            if (isHuge) {
                isHuge = false;
                binding.ivFilterHuge.setImageResource( R.drawable.ic_huge );
                binding.tvHuge.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isHuge = true;
                binding.ivFilterHuge.setImageResource( R.drawable.ic_huge_select );
                binding.tvHuge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.ivFilterPet){
            if (isPet) {
                isPet = false;
                binding.ivFilterPet.setImageResource( R.drawable.ic_pet );
                binding.tvPet.setTextColor( activity.getResources().getColor( R.color.color_c1c1c1 ) );
            } else {
                isPet = true;
                binding.ivFilterPet.setImageResource( R.drawable.ic_pet_select );
                binding.tvPet.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
            }
        } else if (v == binding.filterHeader.tvApplyFilter){
            binding.filterHeader.tvApplyFilter.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );

            Log.e( "IS SMALL SELECTED: ", String.valueOf( isSmall ) );
            Log.e( "IS MEDIUM SELECTED: ", String.valueOf( isMedium ) );
            Log.e( "IS LARGE SELECTED: ", String.valueOf( isLarge ) );
            Log.e( "IS HUGE SELECTED: ", String.valueOf( isHuge ) );
            Log.e( "IS XLARGE SELECTED: ", String.valueOf( isXlarge ) );
            Log.e( "IS PET SELECTED: ", String.valueOf( isPet ) );

            String categories = "0";
            if (isSmall == false && isMedium == false && isLarge == false && isXlarge == false && isHuge == false && isPet == false) {
                isfilteraltered = "0";
            } else {
                isfilteraltered = "1";
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
            Log.e( "Parameter", "" + gson.toString() );
            Intent intent = new Intent();
            activity.setResult(activity.RESULT_OK, intent);
            activity.finish();
        }
    }

    public void setFilters() {
        String response = sharedPref.getFilters();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<FiltersModel>() {}.getType();
        if (!CommonUtils.isTrimEmpty( response )) {
            FiltersModel filtersModel = gson.fromJson( response, collectionType );
            isfilteraltered = filtersModel.getIsFilter().toString();
            String cat[] = filtersModel.getCategory().split( "," );
            if (cat != null && cat.length > 0) {
                for (int i = 0; i < cat.length; i++) {
                    if (cat[i].equals( String.valueOf(  API_Params.SMALL_CATEGORY))){
                        isSmall = true;
                        binding.ivFilterSmall.setImageResource( R.drawable.ic_small_select );
                        binding.tvSmall.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.MEDIUM_CATEGORY ))){
                        isMedium = true;
                        binding.ivFilterMedium.setImageResource( R.drawable.ic_medium_select );
                        binding.tvMedium.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.LARGE_CATEGORY ))){
                        isLarge = true;
                        binding.ivFilterLarge.setImageResource( R.drawable.ic_large_select );
                        binding.tvLarge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.XLARGE_CATEGORY ))){
                        isXlarge = true;
                        binding.ivFilterXlarge.setImageResource( R.drawable.ic_xlarge_select );
                        binding.tvXlarge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.HUGE_CATEGORY ) )){
                        isHuge = true;
                        binding.ivFilterHuge.setImageResource( R.drawable.ic_huge_select );
                        binding.tvHuge.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                    if (cat[i].equals( String.valueOf( API_Params.PET_CATEGORY ))){
                        isPet = true;
                        binding.ivFilterPet.setImageResource( R.drawable.ic_pet_select );
                        binding.tvPet.setTextColor( activity.getResources().getColor( R.color.colorPrimary ) );
                    }
                }
            }
        }
    }
}
