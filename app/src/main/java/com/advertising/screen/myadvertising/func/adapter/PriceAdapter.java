package com.advertising.screen.myadvertising.func.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.databinding.ItemAdapterBinding;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;

/**
 * 作者：leavesC
 * 时间：2019/2/27 21:36
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.UserAdapterHolder> {

    private ObservableArrayList<PriceEntity> beans;
    public PriceAdapter(ObservableArrayList<PriceEntity> userList) {
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
        holder.getBinding().setBean(beans.get(position));
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

    static class UserAdapterHolder extends RecyclerView.ViewHolder {
        private ItemAdapterBinding binding;
        UserAdapterHolder(ItemAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        ItemAdapterBinding getBinding() {
            return binding;
        }
    }
}
