package example.android.ait.hu.practiceroomassistant.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import example.android.ait.hu.practiceroomassistant.PlayRecordingDialog;
import example.android.ait.hu.practiceroomassistant.R;
import example.android.ait.hu.practiceroomassistant.RemoveRecordingDialog;
import example.android.ait.hu.practiceroomassistant.RemoveSongDialog;
import example.android.ait.hu.practiceroomassistant.SongListActivity;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.UserRecordings;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * List adapter for UserRecordings
 */
public class UserRecordingsAdapter extends BaseAdapter {

    //context of the list
    private Context context;

    //index of the song the recordings belong to
    private int index;

    /**
     * Constructor
     * @param context
     * @param songIndex
     */
    public UserRecordingsAdapter(Context context, int songIndex){
        this.context = context;
        index = songIndex;
    }

    /**
     * get the size of the list
     * @return
     */
    @Override
    public int getCount() {
        return SongListActivity.songs.get(index).getRecordings().size();
    }

    /**
     * get the item at the given position
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return SongListActivity.songs.get(index).getRecordings().get(position);
    }

    /**
     * get the id of the given element
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * class that points to the elements of each list element
     */
    public static class ViewHolder {
        TextView tvRecordingName;
        int position;
        UserRecordings recording;

    }

    /**
     * get the view at the given index of the list
     *
     * Uses the view holder pattern
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        //create the viewHolder from the xml
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.recording_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tvRecordingName = (TextView) v.findViewById(R.id.tvRecordingName);
            v.setTag(holder);
        }

        //get the song associated with the list
        Song theSong = SongListActivity.songs.get(index);

        //get the current recording in the list
        UserRecordings record = theSong.getRecordings().get(position);

        //set the values of the UI
        if(record !=null) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.position = position;
            holder.tvRecordingName.setText(record.getLabel());
            holder.recording = record;

            //if the user clicks on the list entry bring up
            //a dialog to play the recording
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayRecordingDialog fragment = new PlayRecordingDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString(PlayRecordingDialog.KEY_RECORDING,  holder.recording.getFileName());
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), RemoveSongDialog.TAG);
                }
            });


            //when the user long clicks bring up a dialog that asks the user
            //if they want to delete the current entry
            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    RemoveRecordingDialog fragment = new RemoveRecordingDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(RemoveRecordingDialog.KEY_RECORDING_INDEX, holder.position);
                    bundle.putInt(RemoveRecordingDialog.KEY_REMOVE, index);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), RemoveRecordingDialog.TAG);
                    return false;
                }
            });

        }

        return v;
    }
}
