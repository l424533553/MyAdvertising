package com.advertising.screen.myadvertising.mvvm;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.advertising.screen.myadvertising.mvvm.main.vm.UIViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * ViewModel的生成工厂，将提供LoginViewModel。
 * Required given LoginViewModel has a non-empty constructor
 * 给定LoginViewModel必须具有非空的构造函数
 * <p>
 * 说明： ViewModel的构造工厂
 */
public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private LifecycleOwner owner;
    private final Application application;

    public ViewModelFactory(@NonNull Application application, @NonNull LifecycleOwner owner) {
        super(application);
        this.application = application;
        this.owner = owner;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       if (modelClass.isAssignableFrom(UIViewModel.class)) {
            return (T) new UIViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

}
