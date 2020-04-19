package net.greenmanov.android.rcloneanime.adapters.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.DriveAdapter;

public class DriveViewHolder extends RecyclerView.ViewHolder {

    public final static int VIEW = R.layout.anime_file_item;

    private DriveAdapter adapter;
    private int position;

    public DriveViewHolder(@NonNull View itemView, DriveAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
    }

    public void bind(int position) {
        this.position = position;
    }
}
