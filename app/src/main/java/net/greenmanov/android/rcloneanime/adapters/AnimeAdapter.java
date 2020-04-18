package net.greenmanov.android.rcloneanime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.data.Anime;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private final List<Anime> allAnime;
    private List<Anime> filteredAnime;

    public AnimeAdapter(Context context, List<Anime> animes) {
        this.mContext = context;
        this.allAnime = new ArrayList<>(animes);
        filteredAnime = this.allAnime;
    }

    @Override
    public int getCount() {
        return filteredAnime.size();
    }

    @Override
    public long getItemId(int position) {
        return filteredAnime.get(position).getId();
    }

    @Override
    public Object getItem(int position) {
        return filteredAnime.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.count = allAnime.size();
                    results.values = allAnime;
                } else {
                    List<Anime> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Anime o : allAnime) {
                        if (o.getName().toUpperCase().contains(searchStr)) {
                            resultsData.add(o);
                        }
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredAnime = (List<Anime>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Anime anime = filteredAnime.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.anime_item, null);

            final ViewHolder viewHolder = new ViewHolder(
                    convertView.findViewById(R.id.imageview_cover_art),
                    convertView.findViewById(R.id.anime_name)
            );
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.imageView.setImageResource(R.drawable.unknown);
        viewHolder.titleTextView.setText(anime.getName());

        if (anime.getImage() != null) {
            Picasso.get().load(anime.getImage()).placeholder(R.drawable.unknown).into(viewHolder.imageView);
        }

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
