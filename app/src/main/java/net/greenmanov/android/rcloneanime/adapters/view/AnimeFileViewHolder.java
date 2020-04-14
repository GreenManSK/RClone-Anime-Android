package net.greenmanov.android.rcloneanime.adapters.view;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.AnimeFileAdapter;

public class AnimeFileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final static int VIEW = R.layout.anime_file_item;

    private AnimeFileAdapter adapter;
    private int position;

    private final MaterialRippleLayout container;
    private final TextView textView;

    public AnimeFileViewHolder(@NonNull View itemView, AnimeFileAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        container = itemView.findViewById(R.id.anime_file);
        textView = itemView.findViewById(R.id.anime_name);

        container.setOnClickListener(this);
    }

    public void bind(int position) {
        this.position = position;

        updateContainer();
        textView.setText(adapter.get(position).getName());
    }

    private void updateContainer() {
        container.setActivated(adapter.isSelected(position));
        container.setRippleColor(adapter.isSelected(position) ? Color.BLACK : Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        adapter.togglSelect(position);
        updateContainer();
        adapter.getOnClickListener().onClick(adapter.get(position));
    }
}
