package com.event.transactions.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.event.transactions.networkcalling.Resource;
import com.event.transactions.model.Transaction;
import com.event.transactions.repository.AppRepository;
import com.event.transactions.request.LoginRequest;
import com.event.transactions.response.LoginResponse;

import java.util.List;

public class AppViewmodel extends ViewModel {
    private final AppRepository appRepository;

    AppViewmodel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public LiveData<Resource<LoginResponse>> login(LoginRequest loginRequest) {
        return appRepository.login(loginRequest);
    }

    public LiveData<Resource<List<Transaction>>> transaction() {
        return appRepository.transaction();
    }

    public LiveData<List<Transaction>> transactionsFromRoom() {
        return appRepository.getTransactionsFromRoom();
    }
}
