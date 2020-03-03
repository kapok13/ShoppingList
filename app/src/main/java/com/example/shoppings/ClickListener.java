package com.example.shoppings;

import com.example.shoppings.data.Shopping;

/*
    Интерфейс для установки слушателя на ImageButton в RecyclerView
    и вытягивания данных из RecyclerView
 */
public interface ClickListener {
    public void onButtonTouch(Shopping shopping);
}
