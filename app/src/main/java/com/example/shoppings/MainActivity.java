package com.example.shoppings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shoppings.DI.App;
import com.example.shoppings.data.Shopping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_ACTIVITY_REQUEST_CODE = 1;

    private ShoppingViewModel mViewModel;

    // Список с отмеченными RadioButton покупками
    public static List<Shopping> checkedShoppingsList;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.recycle_shopping_view)
    RecyclerView mShoppingRecycle;

    @Inject
    ShoppingPresenter mSHoppingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        App.getAppComponent().inject(this);
        mSHoppingPresenter.attachView(this);
        final ShoppingListAdapter adapter = new ShoppingListAdapter(this, new ClickListener() {
            @Override
            public void onButtonTouch(Shopping shopping) {
                mViewModel.deleteShopping(shopping);
            }
        });
        mShoppingRecycle.setAdapter(adapter);
        mShoppingRecycle.setLayoutManager(new LinearLayoutManager(this));
        mViewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        mViewModel.getAllShoppings().observe(this, new Observer<List<Shopping>>() {
            @Override
            public void onChanged(List<Shopping> shoppings) {
                adapter.setShopping(shoppings);
            }
        });
    }
    // Переход к активности добавления новой покупки
    @OnClick(R.id.fab)
    void onAddingButtonClick(){
        mSHoppingPresenter.toShoppingsAdd(this, ADD_ACTIVITY_REQUEST_CODE);
    }

    /*
        Проверяем полученные данные о новой покупке
        Вставляем данные в базу в соответствии тому пришла картинка или текст
     */
    public void onActivityResult(int requestCode, int resultCOde, Intent data){
        super.onActivityResult(requestCode, resultCOde, data);

        if (requestCode == ADD_ACTIVITY_REQUEST_CODE && resultCOde == RESULT_OK){
            Shopping shopping = new Shopping();
            shopping.setProductName(data.getStringExtra(AddPurchaseActivity.EXTRA_REPLY));
            mViewModel.insert(shopping);
        }
        else if (requestCode == ADD_ACTIVITY_REQUEST_CODE && requestCode == RESULT_FIRST_USER){
            Shopping shopping = new Shopping();
            shopping.setProductUri(data.getStringExtra(AddPurchaseActivity.EXTRA_REPLY_WITH_PHOTO));
            mViewModel.insert(shopping);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
        case R.id.shopping_history_menu:
            Переходим к истории покупок

        case R.id.check_all
            Отмечаем все позиции как купленные

        case R.id.mark_few_elements
            Переносим отмеченные RadioButton элементы в историю покупок
            Убираем элементы со списка покупок
            Очищаем список отмеченных элементов
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.shopping_hisoty_menu:
                mSHoppingPresenter.toShoppingHistory(this);
                return true;
            case R.id.check_all:
                mViewModel.deleteAll();
                return  true;
            case R.id.mark_few_elements:
                mViewModel.deleteFewShoppings(checkedShoppingsList);
                checkedShoppingsList.clear();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
