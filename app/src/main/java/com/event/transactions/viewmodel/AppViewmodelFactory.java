package com.event.transactions.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.event.transactions.repository.AppRepository;

import jakarta.inject.Inject;

public class AppViewmodelFactory implements ViewModelProvider.Factory {
    private final AppRepository appRepository;

    @Inject
    AppViewmodelFactory(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AppViewmodel(appRepository);
    }
}
