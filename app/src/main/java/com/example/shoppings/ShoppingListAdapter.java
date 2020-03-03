package com.example.shoppings;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppings.data.Shopping;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder> {

    class ShoppingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView shoppingItemView;
        private final ImageButton shoppingImageButton;
        private final RadioButton shoppingRadioButton;
        private final ImageView photoImage;
        private WeakReference<ClickListener> listenerWeakReference;

        private ShoppingViewHolder(View view, ClickListener listener){
            super(view);
            MainActivity.checkedShoppingsList = new ArrayList<>();
            listenerWeakReference = new WeakReference<>(listener);
            photoImage = view.findViewById(R.id.recycler_photo);
            shoppingItemView = view.findViewById(R.id.item_id);
            shoppingImageButton = view.findViewById(R.id.add_to_bought_button);
            shoppingRadioButton = view.findViewById(R.id.radio_button);
            shoppingImageButton.setOnClickListener(this);
            shoppingRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true){
                        MainActivity.checkedShoppingsList.add(mShopping.get(getAdapterPosition()));
                    }

                }
            });

        }

        @Override
        public void onClick(View v) {
            listenerWeakReference.get().onButtonTouch(mShopping.get(getAdapterPosition()));
        }
    }

    private final LayoutInflater mInflater;
    private List<Shopping> mShopping;
    private final ClickListener mListener;

    ShoppingListAdapter(Context context , ClickListener listener){
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.shopping_list, parent, false);
        return new ShoppingViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        if (mShopping != null){
            Shopping current = mShopping.get(position);
            if (current.getProductName() != null) {
                holder.shoppingItemView.setText(current.getProductName());
            }
            else {
                holder.shoppingItemView.setVisibility(View.GONE);
                holder.photoImage.setVisibility(View.VISIBLE);
                Uri currentPictureUri = Uri.parse(current.getProductUri());
                holder.photoImage.setImageURI(currentPictureUri);
            }
        }
        else {
            holder.shoppingItemView.setText(R.string.empty_list);
        }
    }

    void setShopping(List<Shopping> shopping){
        mShopping = shopping;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mShopping != null){
            return mShopping.size();
        }
        else {
            return 0;
        }
    }
}
