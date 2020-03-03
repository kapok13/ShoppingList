package com.example.shoppings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;


public class ShoppingPresenter {


    private MainActivity  mMainActivity;

    private AddPurchaseActivity mAddPurchaseActivity;

    // Получаем AddPurchaseActivity
    public void attachAddPurchase(AddPurchaseActivity addPurchaseActivity){
        mAddPurchaseActivity = addPurchaseActivity;
    }

    // Получаем MainActivity
    public void attachView(MainActivity mainActivity){
        mMainActivity = mainActivity;
    }

    // Переходим к добавлению новой покупки
    public void toShoppingsAdd(Context context, int requestCode){
        Intent intent = new Intent(context, AddPurchaseActivity.class);
        mMainActivity.startActivityForResult(intent, requestCode);
    }

    // Переход к истории покупок
    public void toShoppingHistory(Context context){
        Intent intent = new Intent(context, ShoppingsHistoryActivity.class);
        context.startActivity(intent);
    }

    // Установка адаптера с историей покупок
    public HIstoryListAdapter attachHistoryAdapter(Context context){
        final HIstoryListAdapter hIstoryListAdapter = new HIstoryListAdapter(context);
        return hIstoryListAdapter;
    }

    // Установка LayoutManager а для RecyclerView с историей покупок
    public void setLayoutManager(RecyclerView recyclerView, Context context){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    // Переход с ShoppingsHistoryActivity к MainActivity
    public void onBackButtonPress(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    // Запрос разрешения на использование камеры
    public void requestForCameraPermission(Context context, int requestCode){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(mAddPurchaseActivity, new String[]{Manifest.permission.CAMERA}, requestCode);
        }
    }

    // Запрос разрешения на запись файлов
    public void requestForStorageChangePermission(Context context, int requestCode){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(mAddPurchaseActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
            , requestCode);
        }
    }

    // Зпрос разрешения на просмотр файлов
    public void requestForStorageReadPermission(Context context, int requestCode){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(mAddPurchaseActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , requestCode);
        }
    }

    // Intent для фотографии с камеры и сохранения фото
    public Uri takePhoto(Uri imageUri, int requestCode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        mAddPurchaseActivity.startActivityForResult(intent, requestCode);
        return imageUri;
    }

    // Intent для получения фото с галереи
    public void takePhotoFromDirectory(int requestCode){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mAddPurchaseActivity.startActivityForResult(pickPhoto, requestCode);
    }


}
