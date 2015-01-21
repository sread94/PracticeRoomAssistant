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
import example.android.ait.hu.practiceroomassistant.RemoveWebURLDialog;
import example.android.ait.hu.practiceroomassistant.SongListActivity;
import example.android.ait.hu.practiceroomassistant.ViewUserURL;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.WebURL;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * List adapter for WebURL
 */
public class WebAdapter extends BaseAdapter {

    //the context of the list
    private Context context;

    //the index of the song
    private int index;

    /**
     * Constructor
     * @param context
     * @param songIndex
     */
    public WebAdapter(Context context, int songIndex){
        this.context = context;
        index = songIndex;
    }

    /**
     * getter for the number of items in the list
     * @return
     */
    @Override
    public int getCount() {
        return SongListActivity.songs.get(index).getUrls().size();
    }

    /**
     * getter for the WebURL at the given index
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return SongListActivity.songs.get(index).getUrls().get(position);
    }

    /**
     * get the item id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * create a class that holds a reference to all of the
     * UI elements of each list entry
     */
    public static class ViewHolder {
        TextView tvWebLabel;
        int position;
    }

    /**
     * get the view for the given position in the list
     *
     * uses the viewHolder pattern
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
            v= inflater.inflate(R.layout.web_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tvWebLabel = (TextView) v.findViewById(R.id.tvWebLabel);
            v.setTag(holder);
        }

        //get song the web adapter is associated with
        Song theSong = SongListActivity.songs.get(index);

        //get the WebURL at this position
        final WebURL url = theSong.getUrls().get(position);

        //set the values of the UI
        if(url !=null) {

            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.position = position;
            holder.tvWebLabel.setText(url.getLabel());

            //when the list entry is clicked
            //display the associated web page
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(context, ViewUserURL.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ViewUserURL.KEY_USER_URL, url.getURL());
                    i.putExtras(bundle);
                    context.startActivity(i);
                }
            });

            /**
             * when the list entry is long clicked
             * bring up a dialog that asks the user
             * if they want to delete the link
             */
            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                     RemoveWebURLDialog fragment = new RemoveWebURLDialog();
                     Bundle bundle = new Bundle();
                     bundle.putInt(RemoveWebURLDialog.KEY_URL_INDEX, holder.position);
                     bundle.putInt(RemoveWebURLDialog.KEY_REMOVE, index);
                     fragment.setArguments(bundle);
                     fragment.show(((Activity)context).getFragmentManager(), RemoveWebURLDialog.TAG);
                     return false;
                }
            });

        }

        return v;
    }
}
