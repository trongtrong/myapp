package androi30_b.my_mp3_app;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Duc Nguyen on 4/13/2017.
 */

public class PlayListAdapter extends BaseAdapter {
    private ArrayList<String> paths;
    private Context context;
    private LayoutInflater inflater;

    public PlayListAdapter(Context context, ArrayList<String> paths) {
        this.context = context;
        this.paths = paths;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder.tvArtist = (TextView) convertView.findViewById(R.id.tv_artist1);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title1);;
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(paths.get(position));
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        holder.tvArtist.setText(artist);
        holder.tvTitle.setText(title);
        return convertView;
    }
    private class Holder{
        TextView tvTitle, tvArtist;
    }
}
