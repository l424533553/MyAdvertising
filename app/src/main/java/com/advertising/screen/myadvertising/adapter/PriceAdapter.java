package com.advertising.screen.myadvertising.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.databinding.ItemAdapterBinding;
import com.axecom.smartweight.my.entity.PriceBean;

/**
 * 作者：leavesC
 * 时间：2019/2/27 21:36
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.UserAdapterHolder> {

    private ObservableArrayList<PriceBean> beans;

    public PriceAdapter(ObservableArrayList<PriceBean> userList) {
        this.beans = userList;
    }

    @NonNull
    @Override
    public UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_adapter, parent, false);
        return new UserAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterHolder holder, int position) {
        holder.getBinding().setPriceBean(beans.get(position));
        holder.getBinding().setPosition(position);
    }

    @Override
    public int getItemCount() {
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    class UserAdapterHolder extends RecyclerView.ViewHolder {
        private ItemAdapterBinding binding;
        UserAdapterHolder(ItemAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemAdapterBinding getBinding() {
            return binding;
        }
    }

}
