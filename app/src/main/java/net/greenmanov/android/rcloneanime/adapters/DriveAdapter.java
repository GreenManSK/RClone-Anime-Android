package net.greenmanov.android.rcloneanime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.adapters.view.DriveViewHolder;
import net.greenmanov.android.rcloneanime.data.Drive;

import java.util.List;

public class DriveAdapter extends RecyclerView.Adapter<DriveViewHolder> {
    private final List<Drive> list;
    private final DriveButtonsListener listener;

    public DriveAdapter(List<Drive> list, DriveButtonsListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DriveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(DriveViewHolder.VIEW, viewGroup, false);

        return new DriveViewHolder(contactView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DriveViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Drive get(int position) {
        return list.get(position);
    }

    public DriveButtonsListener getListener() {
        return listener;
    }

    public interface DriveButtonsListener {
        void onEdit(Drive drive);
        void onRefresh(Drive drive);
        void onDelete(Drive drive);
    }
}
