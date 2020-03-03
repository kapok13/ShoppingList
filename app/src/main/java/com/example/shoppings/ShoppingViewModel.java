package com.example.shoppings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shoppings.data.History;
import com.example.shoppings.data.Shopping;
import com.example.shoppings.data.ShoppingRepository;

import java.util.List;


public class ShoppingViewModel extends AndroidViewModel {

    private ShoppingRepository mShoppingRepository;
    private LiveData<List<Shopping>> mShoppingList;

    // Получаем список покупок
    public ShoppingViewModel(Application application){
        super(application);
        mShoppingRepository = new ShoppingRepository(application);
        mShoppingList = mShoppingRepository.getmAllShoppings();
    }

    // Возвращает список покупок
    public LiveData<List<Shopping>> getAllShoppings(){
        return mShoppingList;
    }

    // Вставка новой покупки
    public void insert(Shopping shopping){
        mShoppingRepository.insert(shopping);
    }

    /*
        Вставляем все покупки в список с иторией покупок
        Убираем покупки со списка покупок
     */
    public void deleteAll(){
        List<Shopping> temporaryShopping = mShoppingList.getValue();
        for (Shopping shopping : temporaryShopping){
            if (shopping.getProductName() != null) {
                String shoppingProduct = shopping.getProductName();
                History history = new History();
                history.setShoppingHistoryItem(shoppingProduct);
                mShoppingRepository.insertHistory(history);
            }
            else {
                String currentShoppingUri = shopping.getProductUri();
                History history = new History();
                history.setHistoryUri(currentShoppingUri);
                mShoppingRepository.insertHistory(history);
            }
        }
        mShoppingRepository.deleteAll();
    }

    /*
        Вставляем покупку в список истории покупок
        Убираем покупку со списка покупок
     */
    public void deleteShopping(Shopping shopping){
        if (shopping.getProductName() != null) {
            History history = new History();
            history.setShoppingHistoryItem(shopping.getProductName());
            mShoppingRepository.insertHistory(history);
        }
        else {
            History history = new History();
            history.setHistoryUri(shopping.getProductUri());
            mShoppingRepository.insertHistory(history);
        }
        mShoppingRepository.deleteShopping(shopping);
    }

    /*
        Вставляем несколько покупок в список истории покупок
        Убирае несколько покупок со списка покупок
     */
    public void deleteFewShoppings(List<Shopping> shoppings){
        for (Shopping shopping :shoppings){
            if (shopping.getProductName() != null) {
                String currentShopping = shopping.getProductName();
                History history = new History();
                history.setShoppingHistoryItem(currentShopping);
                mShoppingRepository.insertHistory(history);
            }
            else {
                History history = new History();
                history.setHistoryUri(shopping.getProductUri());
                mShoppingRepository.insertHistory(history);
            }
            mShoppingRepository.deleteShopping(shopping);
        }

    }


}
