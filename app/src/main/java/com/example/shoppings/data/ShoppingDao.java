package com.example.shoppings.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface ShoppingDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Shopping shopping);

    @Delete
    void deleteShoppings(Shopping shopping);

    @Query("DELETE FROM shopping_table")
    void deleteAll();

    @Query("SELECT * FROM shopping_table")
    LiveData<List<Shopping>> getAll();


}
