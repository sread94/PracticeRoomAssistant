package example.android.ait.hu.practiceroomassistant.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import example.android.ait.hu.practiceroomassistant.R;
import example.android.ait.hu.practiceroomassistant.RemoveSongDialog;
import example.android.ait.hu.practiceroomassistant.SongDetailsActivity;
import example.android.ait.hu.practiceroomassistant.SongListActivity;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;

/**
 * Created by Sarah Read on 11/18/2014.
 *
 * List adapter for the songs
 */
public class SongsAdapter extends BaseAdapter{

    //the list context
    private Context context;

    /**
     * Constructor
     * @param context
     */
    public SongsAdapter(Context context){
        this.context=context;
    }

    /**
     * get the number of songs in the list
     * @return
     */
    @Override
    public int getCount() {
        return SongListActivity.songs.size();
    }

    /**
     * get the song at the given index
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return SongListActivity.songs.get(position);
    }

    /**
     * get the id at the given position
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * class that holds reference to all of the elements of
     * each list attribute
     */
    public static class ViewHolder {
        TextView tvSongStatus;
        TextView tvSongTitle;
        int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        //create the viewHolder from the xml
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.song_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tvSongTitle = (TextView) v.findViewById(R.id.tvSongTitle);
            holder.tvSongStatus = (TextView) v.findViewById(R.id.tvSongStatus);
            v.setTag(holder);
        }

        //get the current song
        Song song = SongListActivity.songs.get(position);

        //set the values of the UI
        if(song !=null) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.position = position;
            holder.tvSongTitle.setText(song.getTitle());
            int stat = song.getComments().getStatus();
            SongComments.StatusEnum theEnum = SongComments.StatusEnum.getItem(stat);
            holder.tvSongStatus.setText(theEnum.toString());

            //if the user clicks the list item show the SongDetailsActivity
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, SongDetailsActivity.class);
                    detailIntent.putExtra(SongDetailsActivity.POSITION_KEY, holder.position + "");
                    detailIntent.putExtra(SongDetailsActivity.START_SCREEN, 0 + "");
                    context.startActivity(detailIntent);
                }
            });

            //if the user long clicks the list item show a dialog to ask
            //if the user wants to delete the song
            v.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    RemoveSongDialog fragment = new RemoveSongDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(RemoveSongDialog.KEY_REMOVE, holder.position);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), RemoveSongDialog.TAG);
                    return false;
                }
            });

            //set the background color based on the status
            if(song.getComments().getStatus()==0){
                v.setBackgroundColor(Color.argb(120,255,0,0));
            }
            else if(song.getComments().getStatus()==1){
                v.setBackgroundColor(Color.argb(150,0,149,182));
            }
            else{
                v.setBackgroundColor(Color.argb(120,0,255,0));
            }
        }

        return v;
    }

}
