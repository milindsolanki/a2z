package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.ItemDetails;
import com.a2z.deliver.models.items.PickupAddress;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.databinding.LayoutHomeListBinding;
import com.a2z.deliver.databinding.LayoutProgressbarBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemDetails> itemDetailsList;
    SharedPref sharedPref;
    Activity activity;
    OnItemClickListener onItemClickListener;
    boolean showLoader = false;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;
    private LayoutInflater layoutInflater;

    public ItemAdapter(Activity activity, List<ItemDetails> itemDetailsList, OnItemClickListener onItemClickListener) {
        this.itemDetailsList = itemDetailsList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
        sharedPref = new SharedPref(activity);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (viewType == TYPE_ITEM) {
            LayoutHomeListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_home_list, parent, false);
            return new MyViewHolder(binding);
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_progressbar, parent, false);
            return new ProgressViewHolder(binding);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        if (_holder instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) _holder;

            final ItemDetails itemDetails = itemDetailsList.get(position);
            holder.binding.setItem( itemDetails );
            PickupAddress pickupAddress = itemDetailsList.get( position ).getPickupAddress();
            DeliveryAddress deliveryAddress = itemDetailsList.get( position ).getDeliveryAddress();
            if (pickupAddress != null && deliveryAddress != null) {
                holder.binding.tvItemKm.setText(itemDetails.getTotalDistance() + " " + sharedPref.getDistanceUnit());
                holder.binding.tvItemSize.setText( itemDetails.getItemType() );
                if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.SMALL_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_small, 0, 0, 0 );
                } else if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.MEDIUM_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_medium, 0, 0, 0 );
                } else if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.LARGE_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_large, 0, 0, 0 );
                } else if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.XLARGE_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_xlarge, 0, 0, 0 );
                } else if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.HUGE_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_huge, 0, 0, 0 );
                } else if (itemDetails.getItemFits().equalsIgnoreCase( API_Params.PET_CATEGORY )){
                    holder.binding.tvItemSize.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_item_pet, 0, 0, 0 );
                }
                holder.binding.layoutMain.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick( itemDetails.getOrderId(),itemDetails.getUserId() );
                    }
                } );

                String date = itemDetails.getReceivingTime();
                String formatedDate = CommonUtils.getFormattedDate( date, "dd/mm/yyyy hh:mm aa", "dd-MMM" );

                if (formatedDate != null)
                    holder.binding.tvItemTime.setText( formatedDate );
            }
        }
    }


    @Override
    public int getItemCount() {
        return (null != itemDetailsList ? itemDetailsList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (itemDetailsList.size() != 0) {
            if ((position == itemDetailsList.size() - 1) && showLoader) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public void setShowLoader(boolean status) {
        showLoader = status;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LayoutHomeListBinding binding;

        public MyViewHolder(final LayoutHomeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        private LayoutProgressbarBinding binding;
        public ProgressViewHolder(LayoutProgressbarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
