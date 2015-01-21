package example.android.ait.hu.practiceroomassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import example.android.ait.hu.practiceroomassistant.adapter.UserRecordingsAdapter;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.UserRecordings;

/**
 * Created by Sarah Read on 11/30/2014.
 *
 * Fragment in View Pager of the song details
 * representing the list of UserRecordings
 */
public class RecordingsFragment extends ListFragment {

    //request code to get recording information back from the
    //activity where the user creates the recording
    public static final int REQUEST_CODE_RECORD = 112;

    //index of the current song
    private int index;

    //the current song
    private Song song;

    /**
     * Initialize values
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(SongDetailsActivity.POSITION_KEY)) {
            //get the index of the song
            int position = Integer.parseInt(getArguments().getString(SongDetailsActivity.POSITION_KEY));
            index = position;
            song = SongListActivity.songs.get(index);
        }

        //set a menu on the fragment
        setHasOptionsMenu(true);

        //set a list adapter for the recordings
        setListAdapter(new UserRecordingsAdapter(getActivity(), index));
    }

    /**
     * Creat the view by setting the layout and the background
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_layout, null);
        rootView.setBackgroundResource(R.drawable.music_bckgrnd_two);
        return rootView;
    }

    /**
     * onResume notify the list adapter that the data has changed
     */
    @Override
    public void onResume() {
        super.onResume();
        ((UserRecordingsAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * set the actions for when each menu item is clicked
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the id of the item selected
        int id = item.getItemId();

        //go to metronome activity
        if (id == R.id.submenu_metronome) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), MetronomeActivity.class);
            startActivity(newIntent);
            return true;
        }

        //go to reminder activity
        if (id == R.id.submenu_reminder) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), ReminderActivity.class);
            startActivity(newIntent);
            return true;
        }

        //go back to main menu
        if (id == R.id.action_main_menu) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), SongListActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            getActivity().finish();
            return true;
        }

        //open the delete song dialog fragment
        if (id == R.id.action_remove_item) {
            RemoveSongDialog fragment = new RemoveSongDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(RemoveSongDialog.KEY_REMOVE, index);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getFragmentManager(), RemoveSongDialog.TAG);
            return true;
        }

        //go to make new recording activity
        if (id == R.id.action_add_section) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), CreateNewRecordingActivity.class);
            startActivityForResult(newIntent, REQUEST_CODE_RECORD);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the menu for this fragment
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_recording_fragment, menu);
    }

    /**
     * When a result is returned to this fragment get the values
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get the recording file and label
        //if neither are null save the recording and add it to the list
        if(requestCode == REQUEST_CODE_RECORD){
            String theRecording = "";
            String theLabel = "";
            try {
                theRecording = data.getStringExtra(CreateNewRecordingActivity.KEY_RECORDING);
            }catch(Exception e){
                return;
            }
            try {
                theLabel = data.getStringExtra(CreateNewRecordingActivity.KEY_RECORDING_LABEL);
            }catch(Exception e){
                return;
            }
            if(!theRecording.equals("") && !theLabel.equals("")){
                UserRecordings newRecording = new UserRecordings(theRecording, song.getTitle(), theLabel);
                newRecording.save();
                SongListActivity.getSongs();
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(SongDetailsActivity.START_SCREEN, 2 + "");
                getActivity().startActivityForResult(intent, 10);
            }
        }
    }

}
