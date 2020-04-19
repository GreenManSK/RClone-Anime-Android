package net.greenmanov.android.rcloneanime.adapters.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.DriveAdapter;
import net.greenmanov.android.rcloneanime.data.Drive;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DriveViewHolder extends RecyclerView.ViewHolder {

    public final static int VIEW = R.layout.drive_item;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd yyyy HH:mm");

    private DriveAdapter adapter;
    private int position;

    private final TextView driveNameTextView;
    private final TextView drivePathTextView;
    private final ImageView watchedIconImageView;
    private final TextView driveAnimeCountTextView;
    private final TextView lastRefreshTextView;

    private final ImageButton editButton;
    private final ImageButton refreshButton;
    private final ImageButton deleteButton;

    public DriveViewHolder(@NonNull View itemView, DriveAdapter adapter) {
        super(itemView);
        this.adapter = adapter;

        driveNameTextView = itemView.findViewById(R.id.drive_name);
        drivePathTextView = itemView.findViewById(R.id.drive_path);
        watchedIconImageView = itemView.findViewById(R.id.watchedIcon);
        driveAnimeCountTextView = itemView.findViewById(R.id.drive_count);
        lastRefreshTextView = itemView.findViewById(R.id.last_refresh);

        editButton = itemView.findViewById(R.id.editButton);
        refreshButton = itemView.findViewById(R.id.refreshButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);

        editButton.setOnClickListener(v -> {
            adapter.getListener().onEdit(adapter.get(this.position));
        });
        refreshButton.setOnClickListener(v -> {
            adapter.getListener().onRefresh(adapter.get(this.position));
        });
        deleteButton.setOnClickListener(v -> {
            adapter.getListener().onDelete(adapter.get(this.position));
        });
    }

    public void bind(int position) {
        this.position = position;
        Drive drive = adapter.get(position);
        driveNameTextView.setText(drive.getName());
        drivePathTextView.setText(drive.getPath());
        watchedIconImageView.setImageResource(drive.isWatched() ? R.drawable.ic_visibility_white_24dp : R.drawable.ic_visibility_off_white_24dp);
        driveAnimeCountTextView.setText(Integer.toString(drive.getAnimeList().size()));

        if (drive.getLastRefresh() == null) {
            lastRefreshTextView.setText(R.string.never);
        } else {
            lastRefreshTextView.setText(drive.getLastRefresh().format(DATE_TIME_FORMATTER));
        }
    }
}
