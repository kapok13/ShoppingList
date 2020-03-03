package com.example.shoppings.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingRepository {

    private ShoppingDao mShoppingDao;

    private HistoryDao mHistoryDao;

    private LiveData<List<History>> mAllHistory;

    private LiveData<List<Shopping>> mAllShoppings;

    // Конструктор для модели со списком итории
    public ShoppingRepository(Application application, String historyCode){
            ShoppingDatabase database = ShoppingDatabase.getDatabase(application);
            mHistoryDao = database.getHistoryDao();
            mAllHistory = mHistoryDao.getHistory();
    }

    // Возвращает историю покупок
    public LiveData<List<History>> getmAllHistory(){
        return mAllHistory;
    }

    // Конструктор для модели с основным списком
    public  ShoppingRepository(Application application){
            ShoppingDatabase database = ShoppingDatabase.getDatabase(application);
            mHistoryDao = database.getHistoryDao();
            mShoppingDao = database.getShoppingDao();
            mAllShoppings = mShoppingDao.getAll();
    }

    // Возвращает список покупок
    public LiveData<List<Shopping>> getmAllShoppings(){
        return mAllShoppings;
    }

    // Вставка покупки в список
    public void insert(Shopping shopping){
        ShoppingDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                mShoppingDao.insert(shopping);
            }
        });
    }

    // Вставка в список с иторией покупок
    public void insertHistory(History history){
        ShoppingDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                mHistoryDao.insertHistory(history);
            }
        });
    }

    // Очистить список покупок
    public void deleteAll(){
        ShoppingDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() { mShoppingDao.deleteAll();
            }
        });
    }

    // Удалить обьект из списка
    public void deleteShopping(Shopping shopping){
        ShoppingDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                mShoppingDao.deleteShoppings(shopping);
            }
        });
    }

}
