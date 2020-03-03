package com.example.shoppings;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppings.data.History;

import java.util.List;

public class HIstoryListAdapter extends RecyclerView.Adapter<HIstoryListAdapter.HistoryViewHolder> {

    private List<History> mHistory;
    private final LayoutInflater mInflater;

    HIstoryListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.history_list, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (mHistory != null){
            History current = mHistory.get(position);
            if (current.getShoppingHistoryItem() != null) {
                holder.historyItemView.setText(current.getShoppingHistoryItem());
            }
            else {
                holder.historyItemView.setVisibility(View.GONE);
                holder.historyImage.setVisibility(View.VISIBLE);
                Uri currentPictureUri = Uri.parse(current.getHistoryUri());
                holder.historyImage.setImageURI(currentPictureUri);
            }
        }
        else {
            holder.historyItemView.setText(R.string.empty_list);
        }
    }

    public void setmHistory(List<History> history){
        mHistory = history;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mHistory != null){
            return mHistory.size();
        }
        else {
            return 0;
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        private final ImageView historyImage;
        private final TextView historyItemView;
        private HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyImage = itemView.findViewById(R.id.recycler_history_photo);
            historyItemView = itemView.findViewById(R.id.history_item_id);
        }
    }
}
