package com.advertising.screen.myadvertising.adapter;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.databinding.ItemAdapterInspectBinding;
import com.advertising.screen.myadvertising.entity.InspectBean;

import java.util.List;

/**
 * 作者：leavesC
 * 时间：2019/2/27 21:36
 * 描述：bingding的适配器
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class InspectAdapter extends RecyclerView.Adapter<InspectAdapter.UserAdapterHolder> {
    private List<InspectBean> beans;

    public InspectAdapter(List<InspectBean> userList) {
        this.beans = userList;
    }

    public InspectAdapter() {

    }

    public void setBeans(List<InspectBean> beans) {
        this.beans = beans;
//        if(beans!=null){
//            notifyDataSetChanged();
//        }
    }

    @NonNull
    @Override
    public UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdapterInspectBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_adapter_inspect, parent, false);
        return new UserAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterHolder holder, int position) {
        holder.getBinding().setInspectBean(beans.get(position));
        holder.getBinding().setPosition(position);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    class UserAdapterHolder extends RecyclerView.ViewHolder {
        private ItemAdapterInspectBinding binding;

        UserAdapterHolder(ItemAdapterInspectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemAdapterInspectBinding getBinding() {
            return binding;
        }
    }
}
