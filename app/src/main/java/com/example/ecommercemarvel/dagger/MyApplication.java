package com.example.ecommercemarvel.dagger;

import android.app.Application;

public class MyApplication extends Application {

    public static ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
}
