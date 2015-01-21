package example.android.ait.hu.practiceroomassistant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import example.android.ait.hu.practiceroomassistant.adapter.ReminderAdapter;
import example.android.ait.hu.practiceroomassistant.data.Reminder;

/**
 * Created by Sarah Read on 11/29/2014.
 *
 * Activity to handle the reminders the users set
 */
public class ReminderActivity extends ListActivity {

    //a list of all of the reminders
    public static ArrayList<Reminder> reminders;

    /**
     * Initialize values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSavedReminders();

        setListAdapter(new ReminderAdapter(this));
    }

    /**
     * on restart notify the list adapter that the data
     * set has changed
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        ((ReminderAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /**
     * set the menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    /**
     * Set actions on each menu item clicked
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the id of the item clicked
        int id = item.getItemId();

        //return to the main menu
        if (id == R.id.action_main_menu) {
            Intent newIntent = new Intent();
            newIntent.setClass(this, SongListActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            finish();
            return true;
        }

        //go to the metronome activity
        if(id ==R.id.submenu_metronome){
            Intent newIntent = new Intent();
            newIntent.setClass(this, MetronomeActivity.class);
            startActivity(newIntent);
            return true;
        }

        //go to the reminder activity
        if(id == R.id.add_reminder){
            AddReminderDialog fragment = new AddReminderDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(AddReminderDialog.INDEX_KEY, -1);
            fragment.setArguments(bundle);
            fragment.show(getFragmentManager(), AddNewSongDialog.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get the reminders saved in the database
     * and set the reminders array list
     */
    public static void getSavedReminders(){
        ArrayList<Reminder> reminderList = (ArrayList<Reminder>) Reminder.listAll(Reminder.class);
        reminders = new ArrayList<Reminder>();
        for(int i = 0; i<reminderList.size(); i++){
            reminders.add(reminderList.get(i));
        }
    }

}
