package com.example.shoppings.DI;

import com.example.shoppings.AddPurchaseActivity;
import com.example.shoppings.MainActivity;
import com.example.shoppings.ShoppingPresenter;
import com.example.shoppings.ShoppingsHistoryActivity;

import dagger.Component;

@Component(modules = PresenterModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void addingInject(AddPurchaseActivity addPurchaseActivity);

    void injectHistory(ShoppingsHistoryActivity shoppingsHistoryActivity);
}
