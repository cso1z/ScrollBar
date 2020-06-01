package com.xyz.scrollbar.weight;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * created by shenyonghui on 2020/5/16
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int[] colors = new int[]{0xFF111111, 0xFF444444, 0xFF888888, 0xFFFFFFFF};

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = new TextView(parent.getContext());
        view.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 300);
        view.setLayoutParams(layoutParams);
        return new RecyclerView.ViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    public void addData() {
        size = size + 4;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(colors[position % colors.length]);
        ((TextView) holder.itemView).setText(String.valueOf(position));
        holder.itemView.setTag(position);
    }

    int size = 4;

    @Override
    public int getItemCount() {
        return size;
    }
}
