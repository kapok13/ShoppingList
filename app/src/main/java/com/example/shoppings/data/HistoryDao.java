package com.example.shoppings.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("SELECT * FROM history_of_shopping_table")
    LiveData<List<History>> getHistory();
}
