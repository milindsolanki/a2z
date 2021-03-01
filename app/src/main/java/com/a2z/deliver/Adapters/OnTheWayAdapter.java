package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.LayoutOnthewayBinding;
import com.a2z.deliver.databinding.LayoutProgressbarBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.interfaces.OnItemDetailClickListeners;
import com.a2z.deliver.models.onTheWay.OnTheWayDetails;

import java.util.List;

public class OnTheWayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OnTheWayDetails> onTheWayDetailsList;
    Activity activity;
    private LayoutInflater layoutInflater;
    OnItemDetailClickListeners onItemClickListener;
    boolean showLoader = false;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;
    String TAG = "ONTHEWAYADAPTER";

    public OnTheWayAdapter(List<OnTheWayDetails> onTheWayDetailsList, Activity activity, OnItemDetailClickListeners onItemClickListener) {
        this.onTheWayDetailsList = onTheWayDetailsList;
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
            LayoutOnthewayBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_ontheway, parent, false );
            return new ItemViewHolder( binding );
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_progressbar, parent, false );
            return new ProgressbarViewHolder( binding );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        if (_holder instanceof ItemViewHolder){
            ItemViewHolder holder = (ItemViewHolder) _holder;
            final OnTheWayDetails onTheWayDetails = onTheWayDetailsList.get( position );
            holder.binding.setItem( onTheWayDetails );
            if (onTheWayDetails != null){
                holder.binding.layoutOntheway.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick( onTheWayDetails );
                    }
                } );
            } else {
                Log.e(TAG, "You are getting null OnTheWayDetails Model");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != onTheWayDetailsList ? onTheWayDetailsList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (onTheWayDetailsList.size() != 0){
            if ((position == onTheWayDetailsList.size() - 1) && showLoader){
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public void setShowLoader(boolean status){
        showLoader = status;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private LayoutOnthewayBinding binding;
        public ItemViewHolder(LayoutOnthewayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class ProgressbarViewHolder extends RecyclerView.ViewHolder {
        private LayoutProgressbarBinding binding;
        public ProgressbarViewHolder(LayoutProgressbarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
