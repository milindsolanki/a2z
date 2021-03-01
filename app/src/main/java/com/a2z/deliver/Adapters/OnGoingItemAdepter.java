package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.a2z.deliver.R;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.mySend.OnGoingItemsResult;
import java.util.List;

public class OnGoingItemAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OnGoingItemsResult> onGoingItemsResults;
    Activity activity;
    OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;

    public OnGoingItemAdepter(List<OnGoingItemsResult> onGoingItemsResults, Activity activity, OnItemClickListener onItemClickListener) {
        this.onGoingItemsResults = onGoingItemsResults;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater=LayoutInflater.from( parent.getContext() );
        }
        /*if (viewType == TYPE_ITEM) {
            LayoutHomeListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_ongoing, parent, false);
            return new MyViewHolder(binding);
        } else if (viewType == TYPE_FOOTER) {
            LayoutProgressbarBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_progressbar, parent, false);
            return new ProgressViewHolder(binding);
        } else {
            return null;
        }*/return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (null != onGoingItemsResults ? onGoingItemsResults.size() : 0);
    }

  /*  public class MyViewHolder extends RecyclerView.ViewHolder{

    }*/
}
