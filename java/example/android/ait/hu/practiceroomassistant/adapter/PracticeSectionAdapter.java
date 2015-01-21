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

import example.android.ait.hu.practiceroomassistant.AddPracticeSectionDialog;
import example.android.ait.hu.practiceroomassistant.R;
import example.android.ait.hu.practiceroomassistant.RemovePracticeSectionDialog;
import example.android.ait.hu.practiceroomassistant.SongListActivity;
import example.android.ait.hu.practiceroomassistant.data.PracticeSections;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;

/**
 * Created by Sarah Read on 11/19/2014.
 *
 * list adapter for the list of practice sections
 */
public class PracticeSectionAdapter extends BaseAdapter {

    //context of the list
    private Context context;

    //index of the song the list is associated with
    private int index;

    /**
     * Constructor
     * @param context
     * @param songIndex
     */
    public PracticeSectionAdapter(Context context, int songIndex){
        this.context = context;
        index = songIndex;
    }

    /**
     * getter for the number of practice sections in the list
     * @return
     */
    @Override
    public int getCount() {
        return SongListActivity.songs.get(index).getPracticeSections().size();
    }

    /**
     * getter for the selected item
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return SongListActivity.songs.get(index).getPracticeSections().get(position);
    }

    /**
     * get the id of the selected item
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Class that holds a reference to all of the UI elements
     * of each list element
     */
    public static class ViewHolder {
        TextView tvSectionName;
        TextView tvSectionType;
        TextView tvSectionNotes;
        TextView tvSectionStat;
        TextView tvSectionTime;
        int position;
    }

    /**
     * getter for the view at a current list position
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
            v= inflater.inflate(R.layout.section_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tvSectionName = (TextView) v.findViewById(R.id.tvSectionName);
            holder.tvSectionType = (TextView) v.findViewById(R.id.tvSectionType);
            holder.tvSectionNotes = (TextView) v.findViewById(R.id.tvSectionNotes);
            holder.tvSectionStat = (TextView) v.findViewById(R.id.tvSectionStat);
            holder.tvSectionTime = (TextView) v.findViewById(R.id.tvSectionTime);
            v.setTag(holder);
        }

        //get the current song
        Song theSong = SongListActivity.songs.get(index);

        //get the current section
        PracticeSections section = theSong.getPracticeSections().get(position);

        //set the values of the UI
        if(section !=null) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.position = position;
            holder.tvSectionName.setText(section.getSection());
            holder.tvSectionNotes.setText(section.getNotesOnSection());
            holder.tvSectionStat.setText(SongComments.StatusEnum.getItem(section.getStatus()).toString());
            holder.tvSectionTime.setText(section.getTimePracticed());
            holder.tvSectionType.setText(section.getTypeOfSection());

            //when the user clicks the list entry
            //allow them to edit the practice section
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    AddPracticeSectionDialog fragment = new AddPracticeSectionDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AddPracticeSectionDialog.CREATE_PRSECTION_KEY, index);
                    bundle.putInt(AddPracticeSectionDialog.NEW_PRSECT, holder.position);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), AddPracticeSectionDialog.TAG);
                }
            });

            //when the user long clicks the list entry
            //pop up a dialog to ask if they would like to delete the list item
            v.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    RemovePracticeSectionDialog fragment = new RemovePracticeSectionDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(RemovePracticeSectionDialog.KEY_REMOVE_SECTION, holder.position);
                    bundle.putInt(RemovePracticeSectionDialog.KEY_REMOVE_SONG, index);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), RemovePracticeSectionDialog.TAG);
                    return false;
                }
            });

            //set the background color based on the value of status associated with the section
            if(section.getStatus()==0){
                v.setBackgroundColor(Color.argb(120,255,0,0));
            }
            else if(section.getStatus()==1){
                v.setBackgroundColor(Color.argb(150,0,149,182));
            }
            else{
                v.setBackgroundColor(Color.argb(120,0,255,0));
            }
        }

        return v;
    }

}
