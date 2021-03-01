package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.LayoutOngoingBinding;
import com.a2z.deliver.databinding.LayoutProgressbarBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.onGoing.OnGoingItemsDetails;

import java.util.List;

public class OnGoingItemAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List <OnGoingItemsDetails> onGoingItemsResults;
    Activity activity;
    OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;
    boolean showLoader = false;

    public OnGoingItemAdapter(List <OnGoingItemsDetails> onGoingItemsResults, Activity activity, OnItemClickListener onItemClickListener) {
        this.onGoingItemsResults = onGoingItemsResults;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from( parent.getContext( ) );
        }
        if (viewType == TYPE_ITEM) {
            LayoutOngoingBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_ongoing, parent, false );
            return new MyViewHolder( binding );
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_progressbar, parent, false );
            return new ProgressBarViewHolder( binding );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder= ( MyViewHolder ) holder;
            final OnGoingItemsDetails onGoingItemsDetail=onGoingItemsResults.get( position );
            myViewHolder.binding.setItem( onGoingItemsDetail );
            if (onGoingItemsDetail != null){
                myViewHolder.binding.layoutOngoing.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick( onGoingItemsDetail.getOrderId(),null );
                    }
                } );
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != onGoingItemsResults ? onGoingItemsResults.size( ) : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (onGoingItemsResults.size( ) != 0) {
            if (position == onGoingItemsResults.size( ) - 1 && showLoader) {
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }
        return super.getItemViewType( position );
    }

    public void setShowLoader(boolean status) {
        showLoader = status;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LayoutOngoingBinding binding;

        public MyViewHolder(LayoutOngoingBinding binding) {
            super( binding.getRoot( ) );
            this.binding = binding;

        }
    }


    private class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        private LayoutProgressbarBinding binding;

        public ProgressBarViewHolder(LayoutProgressbarBinding binding) {
            super( binding.getRoot( ) );
            this.binding = binding;
        }
    }
}
