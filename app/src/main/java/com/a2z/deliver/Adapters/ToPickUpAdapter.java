package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.LayoutProgressbarBinding;
import com.a2z.deliver.databinding.LayoutTopickupBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.PickupAddress;
import com.a2z.deliver.models.toPickUp.ToPickUpDetails;
import com.google.gson.Gson;

import java.util.List;

public class ToPickUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ToPickUpDetails> pickUpDetailsList;
    Activity activity;
    private LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;
    boolean showLoader = false;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;

    public ToPickUpAdapter(List<ToPickUpDetails> pickUpDetailsList, Activity activity, OnItemClickListener onItemClickListener) {
        this.pickUpDetailsList = pickUpDetailsList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext() );
        }
        if (viewType == TYPE_ITEM) {
            LayoutTopickupBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_topickup, parent, false );
            return new ItemViewHolder( binding );
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_progressbar, parent, false );
            return new ProgressBarViewHolder( binding );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        if (_holder instanceof ItemViewHolder){
            ItemViewHolder holder = (ItemViewHolder) _holder;
            final ToPickUpDetails pickUpDetails = pickUpDetailsList.get( position );
            holder.binding.setItem( pickUpDetails );
            if (pickUpDetails != null){
                holder.binding.layoutTopickup.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick( pickUpDetails.getOrderId(),null );
                    }
                } );
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != pickUpDetailsList ? pickUpDetailsList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (pickUpDetailsList.size() != 0){
            if ((position == pickUpDetailsList.size() - 1) && showLoader){
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }
        return super.getItemViewType( position );
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private LayoutTopickupBinding binding;

        public ItemViewHolder(LayoutTopickupBinding binding) {
            super( binding.getRoot() );
            this.binding = binding;
        }
    }

    public void setShowLoader(boolean status){
        showLoader = status;
    }

    private class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        private LayoutProgressbarBinding binding;

        public ProgressBarViewHolder(LayoutProgressbarBinding binding) {
            super( binding.getRoot() );
            this.binding = binding;
        }
    }
}
