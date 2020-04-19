package net.greenmanov.android.rcloneanime.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.adapters.view.AnimeFileViewHolder;
import net.greenmanov.android.rcloneanime.data.AnimeFileEntity;

import java.util.List;

public class AnimeFileAdapter extends RecyclerView.Adapter<AnimeFileViewHolder> {

    private final List<AnimeFileEntity> list;
    private final SparseBooleanArray selected;
    private final OnClickListener onClickListener;

    public AnimeFileAdapter(List<AnimeFileEntity> list, OnClickListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.selected = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public AnimeFileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(AnimeFileViewHolder.VIEW, viewGroup, false);

        return new AnimeFileViewHolder(contactView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeFileViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public AnimeFileEntity get(int poistion) {
        return list.get(poistion);
    }

    public void togglSelect(int position) {
        if (selected.get(position, false)) {
            selected.delete(position);
        } else {
            selected.put(position, true);
        }
    }

    public boolean isSelected(int position) {
        return selected.get(position, false);
    }

    public int getSelectedCount() {
        return selected.size();
    }

    public void selectAll() {
        for (int i = 0; i < list.size(); i++) {
            selected.put(i, true);
        }
        this.notifyDataSetChanged();
    }

    public void clearSelect() {
        selected.clear();
        this.notifyDataSetChanged();
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @FunctionalInterface
    public interface OnClickListener {
        void onClick(AnimeFileEntity file);
    }
}
