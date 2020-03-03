package com.example.shoppings.DI;

import android.app.Application;

public class App extends Application {
    private static  AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
