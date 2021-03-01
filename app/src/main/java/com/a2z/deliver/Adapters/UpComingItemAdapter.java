package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.LayoutProgressbarBinding;
import com.a2z.deliver.databinding.LayoutUpcomingBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.upComeingItem.UpComingItemDetails;

import java.util.List;

public class UpComingItemAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<UpComingItemDetails> upComingItemDetailslist;
    Activity activity;
    private LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;
    boolean showLoader = false;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;

    public UpComingItemAdapter(List <UpComingItemDetails> upComingItemDetailslist, Activity activity, OnItemClickListener onItemClickListener) {
        this.upComingItemDetailslist = upComingItemDetailslist;
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
            LayoutUpcomingBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_upcoming, parent, false );
            return new ItemViewHolder( binding );
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate( layoutInflater, R.layout.layout_progressbar, parent, false );
            return new ProgressBarViewHolder( binding );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder= ( ItemViewHolder ) holder;
            final UpComingItemDetails upComingItemDetail=upComingItemDetailslist.get( position );
            itemViewHolder.binding.setItem( upComingItemDetail );
            if(upComingItemDetail!=null){
                itemViewHolder.binding.layoutUpcoming.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick( upComingItemDetail.getOrderId(),null );
                    }
                } );
            }
        }

    }

    @Override
    public int getItemCount() {
        return (null != upComingItemDetailslist ? upComingItemDetailslist.size( ) : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (upComingItemDetailslist.size( ) != 0) {
            if ((position == upComingItemDetailslist.size( ) - 1) && showLoader) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public void setShowLoader(boolean status) {
        showLoader = status;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LayoutUpcomingBinding binding;

        public ItemViewHolder(LayoutUpcomingBinding binding) {
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
