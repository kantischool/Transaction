package com.event.transactions.component;

import android.content.Context;

import com.event.transactions.ui.BioMetricActivity;
import com.event.transactions.ui.MainActivity;
import com.event.transactions.ui.TransactionActivity;
import com.event.transactions.di.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {

    void injectMainActivity(MainActivity mainActivity);

    void injectTransactionActivity(TransactionActivity transactionActivity);

    void injectBioMetricActivity(BioMetricActivity transactionActivity);

    @Component.Factory
    interface Factory {
        ApplicationComponent create(@BindsInstance Context context);
    }
}
