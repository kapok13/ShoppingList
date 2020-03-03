package com.example.shoppings.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Shopping.class, History.class}, version = 1, exportSchema = false)
public abstract class ShoppingDatabase extends RoomDatabase {

    public abstract ShoppingDao getShoppingDao();

    public abstract HistoryDao getHistoryDao();

    private static  volatile ShoppingDatabase INSTANCE;

    static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    //Закрываем бд в синглтон

    static ShoppingDatabase getDatabase(Context context){
        if (INSTANCE == null){
            synchronized (ShoppingDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoppingDatabase.class,
                            "shopping database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}