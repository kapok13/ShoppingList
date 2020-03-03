package com.example.shoppings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppings.DI.App;
import com.example.shoppings.data.History;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingsHistoryActivity extends AppCompatActivity {

    HistoryViewModel mHistoryViewModel;

    @Inject
    ShoppingPresenter mShoppingPresenter;

    @BindView(R.id.history_recycle_view)
    RecyclerView mHistoryRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
    }

    @OnClick(R.id.back_to_main_button)
    void onBackButtonPressed(){
        mShoppingPresenter.onBackButtonPress(this);
    }

    private void init(){
        ButterKnife.bind(this);
        App.getAppComponent().injectHistory(this);
        HIstoryListAdapter adapter = mShoppingPresenter.attachHistoryAdapter(this);
        mHistoryRecycler.setAdapter(adapter);
        mShoppingPresenter.setLayoutManager(mHistoryRecycler, this);
        mHistoryViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        mHistoryViewModel.getAllShoppings().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setmHistory(histories);
            }
        });

    }
}
