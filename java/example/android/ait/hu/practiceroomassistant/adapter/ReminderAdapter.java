package example.android.ait.hu.practiceroomassistant.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import example.android.ait.hu.practiceroomassistant.AddNewSongDialog;
import example.android.ait.hu.practiceroomassistant.AddReminderDialog;
import example.android.ait.hu.practiceroomassistant.R;
import example.android.ait.hu.practiceroomassistant.ReminderActivity;
import example.android.ait.hu.practiceroomassistant.RemoveReminderDialog;
import example.android.ait.hu.practiceroomassistant.data.Reminder;

/**
 * Created by Sarah Read on 11/29/2014.
 *
 * List adapter for the practice reminders
 */
public class ReminderAdapter extends BaseAdapter{

    //context of the list
    private Context context;

    /**
     * Constructor
     * @param context
     */
    public ReminderAdapter(Context context){
        this.context = context;
    }

    /**
     * getter for the size of the list
     * @return
     */
    @Override
    public int getCount() {
        return ReminderActivity.reminders.size();
    }

    /**
     * getter for the reminder at the given position
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return ReminderActivity.reminders.get(position);
    }

    /**
     * getter for the specified id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Class that holds a reference to the UI elements
     * of each list item
     */
    public static class ViewHolder {
        TextView tvDay;
        TextView tvTime;
        TextView tvMessage;
        TextView tvRepeat;
        int position;
    }

    /**
     * getter for the view at the given position
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
            v= inflater.inflate(R.layout.reminder_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tvDay = (TextView) v.findViewById(R.id.tvReminderDay);
            holder.tvTime = (TextView) v.findViewById(R.id.tvReminderTime);
            holder.tvMessage = (TextView) v.findViewById(R.id.tvReminderMessage);
            holder.tvRepeat = (TextView) v.findViewById(R.id.tvReminderRepeat);
            v.setTag(holder);
        }

        //get the current reminder
        Reminder reminder = ReminderActivity.reminders.get(position);
        //set the values of the UI
        if(reminder !=null) {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.position = position;
            holder.tvDay.setText(Reminder.dayEnum.getItem(reminder.getDay()).toString());

            //put a 0 in front of the hour if it is one digit
            String hourString = reminder.getStartHour()+"";
            if(reminder.getStartHour()<10){
                hourString = "0"+hourString;
            }

            //put a 0 in front of the minute if it is one digit
            String minString = reminder.getStartMin() +"";
            if(reminder.getStartMin()<10){
                minString= "0"+minString;
            }

            //set the text view values
            holder.tvTime.setText(hourString + ":" + minString);
            if(reminder.getMessage().equals("")){
                holder.tvMessage.setText("None");
            }
            else {
                holder.tvMessage.setText(reminder.getMessage());
            }
            if(reminder.isRepeat()) {
                holder.tvRepeat.setText("Yes");
            }
            else{
                holder.tvRepeat.setText("No");
            }

            //if the user clicks on the list entry
            //allow the user to edit the reminder
            v.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AddReminderDialog fragment = new AddReminderDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AddReminderDialog.INDEX_KEY, holder.position);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), AddNewSongDialog.TAG);
                }

            });

            //if the user long clicks on the list entry
            //pop up a dialog to ask if the user wants to delete the reminder
            v.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    RemoveReminderDialog fragment = new RemoveReminderDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(RemoveReminderDialog.KEY_REMOVE, holder.position);
                    fragment.setArguments(bundle);
                    fragment.show(((Activity)context).getFragmentManager(), RemoveReminderDialog.TAG);
                    return false;
                }
            });

        }

        return v;
    }
}
