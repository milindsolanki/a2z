package com.a2z.deliver.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.LayoutAddressListAdapterBinding;
import com.a2z.deliver.interfaces.OnAddressClickListener;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.views.CustomTextview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseLocationAdapter extends RecyclerView.Adapter<ChooseLocationAdapter.MyViewHolder> {
    public List<AddressDetail> addressDetailList;
    OnAddressClickListener onAddressClickListener;
    Activity activity;
    private LayoutInflater layoutInflater;

    public ChooseLocationAdapter(Activity activity, List<AddressDetail> addressDetailList, OnAddressClickListener onAddressClickListener) {
        this.activity = activity;
        this.addressDetailList = addressDetailList;
        this.onAddressClickListener = onAddressClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutAddressListAdapterBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_address_list_adapter, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final AddressDetail addressDetail = addressDetailList.get(position);
        holder.binding.setModel( addressDetail );

        if (addressDetail.getDisplayName().equals("Add Home") || addressDetail.getDisplayName().equals("Add Office")) {
            Log.e("addO", addressDetail.getDisplayName());
            if (addressDetail.getDisplayName().equals("Add Home")) {
                holder.binding.ivHomeimage.setImageResource(R.drawable.ic_home_black);

            } else if (addressDetail.getDisplayName().equals("Add Office")) {
                holder.binding.ivHomeimage.setImageResource(R.drawable.ic_officebag);

            }
        } else if (addressDetail.getDisplayName().equals("Home") || addressDetail.getDisplayName().equals("Office")) {
            if (addressDetail.getDisplayName().equals("Home")) {
                holder.binding.ivHomeimage.setImageResource(R.drawable.ic_home_black);
                holder.binding.ivEditimage.setImageResource(R.drawable.ic_edit);
            } else if (addressDetail.getDisplayName().equals("Office")) {
                holder.binding.ivHomeimage.setImageResource(R.drawable.ic_officebag);
                holder.binding.ivEditimage.setImageResource(R.drawable.ic_edit);
            }
        } else {
            holder.binding.tvAddressTitle.setText(addressDetail.getCompanyName());
            holder.binding.ivEditimage.setVisibility(View.INVISIBLE);
            holder.binding.ivHomeimage.setVisibility(View.INVISIBLE);
        }

        holder.binding.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressClickListener.onAddressClick(addressDetail);
            }
        });
        holder.binding.ivEditimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressClickListener.onEditClick(addressDetail);
            }
        });
    }


    @Override
    public int getItemCount() {
        return addressDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LayoutAddressListAdapterBinding binding;
        public MyViewHolder(LayoutAddressListAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}