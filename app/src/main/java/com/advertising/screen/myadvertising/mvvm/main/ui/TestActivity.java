package com.advertising.screen.myadvertising.mvvm.main.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.databinding.ActivityTestBinding;
import com.advertising.screen.myadvertising.mvvm.ViewModelFactory;
import com.advertising.screen.myadvertising.mvvm.main.vm.UIViewModel;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);

        ViewModelFactory factory = new ViewModelFactory(getApplication(), this);
        UIViewModel viewModel = ViewModelProviders.of(this, factory).get(UIViewModel.class);
        binding.setViewModel(viewModel);

    }
}
