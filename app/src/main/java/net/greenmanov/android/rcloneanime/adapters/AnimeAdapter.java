package net.greenmanov.android.rcloneanime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.data.Anime;

public class AnimeAdapter extends BaseAdapter {

    private final Context mContext;
    private final Anime[] animes;

    public AnimeAdapter(Context context, Anime[] animes) {
        this.mContext = context;
        this.animes = animes;
    }

    @Override
    public int getCount() {
        return animes.length;
    }

    @Override
    public long getItemId(int position) {
        return animes[position].getId();
    }

    @Override
    public Object getItem(int position) {
        return animes[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Anime anime = animes[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.anime_item, null);

            final ViewHolder viewHolder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.imageview_cover_art),
                    (TextView) convertView.findViewById(R.id.textview_book_name)
            );
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.imageView.setImageResource(R.drawable.unknown);
        viewHolder.titleTextView.setText(anime.getName());

        return convertView;
    }

    private class ViewHolder {
        private final ImageView imageView;
        private final TextView titleTextView;

        public ViewHolder(ImageView imageView, TextView titleTextView) {
            this.imageView = imageView;
            this.titleTextView = titleTextView;
        }
    }
}
