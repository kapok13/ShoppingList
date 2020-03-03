package com.example.shoppings;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.shoppings.DI.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPurchaseActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "text_reply";

    public static final String EXTRA_REPLY_WITH_PHOTO = "photo_reply";

    private static final int CAMERA_REQUEST_CODE = 1;

    private static final int STORAGE_CHANGE_REQUEST_CODE = 1;

    private static final int TAKE_PHOTO = 1;

    private static final int STORAGE_READ_REQUEST_CODE = 1;

    private static final int TAKE_PHOTO_FROM_STORAGE = 2;

    // Uri для картинки пользователя
    private Uri imageUri;

    @Inject
    ShoppingPresenter mShoppingPresenter;

    @BindView(R.id.shops_edit_text)
    EditText purchaseText;

    @BindView(R.id.user_photo)
    ImageView userPhotoImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        App.getAppComponent().addingInject(this);
        mShoppingPresenter.attachAddPurchase(this);
    }

    @OnClick(R.id.add_photo_button)
    void onPhotoButtonClick(){
        mShoppingPresenter.requestForCameraPermission(this, CAMERA_REQUEST_CODE);
        mShoppingPresenter.requestForStorageChangePermission(this, STORAGE_CHANGE_REQUEST_CODE);
        imageUri = mShoppingPresenter.takePhoto(imageUri, TAKE_PHOTO);
    }

    @OnClick(R.id.add_photo_from_directory)
    void onDirectoryButtonClick(){
        mShoppingPresenter.requestForStorageReadPermission(this, STORAGE_READ_REQUEST_CODE);
        mShoppingPresenter.takePhotoFromDirectory(TAKE_PHOTO_FROM_STORAGE);
    }
    /*
        Проверяем ввел ли пользователь текст или вставил картинку
        результат посылаем в MainActivity
        завершаем активити
     */
    @OnClick(R.id.adding_fab)
    void onAddingFabClick(){
        if (userPhotoImage.getDrawable() == null) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(purchaseText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String purchase = purchaseText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, purchase);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
        else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_WITH_PHOTO, imageUri.toString());
            setResult(RESULT_FIRST_USER, replyIntent);
        }
        finish();
    }
    /*
        Определяем хочет ли пользователь взять картинку с гелереи или сделать фото
        вытягиваем Uri картинки и ставим ImageView
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver contentResolver = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                        userPhotoImage.setImageBitmap(bitmap);
                    } catch (Exception e){
                        Toast.makeText(this, R.string.photo_load_failed, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case TAKE_PHOTO_FROM_STORAGE:
                if (resultCode == Activity.RESULT_OK){
                    assert data != null;
                    imageUri = data.getData();
                    userPhotoImage.setImageURI(imageUri);
                }
                break;
        }
    }

}
