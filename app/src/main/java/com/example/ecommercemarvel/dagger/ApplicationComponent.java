package com.example.ecommercemarvel.dagger;

import com.example.ecommercemarvel.ui.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkModule.class)
public interface ApplicationComponent {

    void inject(HomeFragment homeFragment);
}
