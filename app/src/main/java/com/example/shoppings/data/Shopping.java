package com.example.shoppings.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_table")
public class Shopping {


    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "product_uri")
    public String productUri;

    @ColumnInfo(name = "product")
    public String productName;

    public String getProductUri() {
        return productUri;
    }

    public void setProductUri(String productUri) {
        this.productUri = productUri;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }
}
