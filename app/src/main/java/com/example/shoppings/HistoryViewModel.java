package com.example.shoppings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shoppings.data.History;
import com.example.shoppings.data.ShoppingRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private static final String HISTORY_CODE = "OK";
    private ShoppingRepository mShoppingRepository;
    private LiveData<List<History>> mHistoryList;

    // Получаем список с историей покупок
    public HistoryViewModel(Application application){
        super(application);
        mShoppingRepository = new ShoppingRepository(application, HISTORY_CODE);
        mHistoryList = mShoppingRepository.getmAllHistory();
    }

    // Возвращает список с иторией покупок
    public LiveData<List<History>> getAllShoppings(){
        return mHistoryList;
    }
}
