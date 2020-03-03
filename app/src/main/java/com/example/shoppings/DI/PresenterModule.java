package com.example.shoppings.DI;

import com.example.shoppings.ShoppingPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    ShoppingPresenter provideShoppingPresenter(){
        return new ShoppingPresenter();
    }
}
