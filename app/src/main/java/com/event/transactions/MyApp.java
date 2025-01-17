package com.event.transactions;

import android.app.Application;

import com.event.transactions.component.ApplicationComponent;
import com.event.transactions.component.DaggerApplicationComponent;

public class MyApp extends Application {

    public ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.factory().create(this);
    }
}
