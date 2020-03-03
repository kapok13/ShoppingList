package com.example.shoppings.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_of_shopping_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "history_uri")
    public String historyUri;

    @ColumnInfo(name = "shitory_product")
    public String shoppingHistoryItem;

    public String getHistoryUri() {
        return historyUri;
    }

    public void setHistoryUri(String historyUri) {
        this.historyUri = historyUri;
    }

    public String getShoppingHistoryItem() {
        return shoppingHistoryItem;
    }

    public void setShoppingHistoryItem(@NonNull String shoppingHistoryItem) {
        this.shoppingHistoryItem = shoppingHistoryItem;
    }
}
