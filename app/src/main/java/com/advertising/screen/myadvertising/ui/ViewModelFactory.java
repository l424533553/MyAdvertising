package com.advertising.screen.myadvertising.ui;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.advertising.screen.myadvertising.data.ScreenDataSource;
import com.advertising.screen.myadvertising.data.ScreenRepository;
import com.advertising.screen.myadvertising.ui.screen.ScreenViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * ViewModel的生成工厂，将提供LoginViewModel。
 * Required given LoginViewModel has a non-empty constructor
 * 给定LoginViewModel必须具有非空的构造函数
 *
 * 说明： ViewModel的构造工厂
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScreenViewModel.class)) {
            return (T) new ScreenViewModel(ScreenRepository.getInstance(new ScreenDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
