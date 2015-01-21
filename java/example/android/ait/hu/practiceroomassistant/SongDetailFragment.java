package example.android.ait.hu.practiceroomassistant;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;

/**
 * Created by Sarah Read on 11/24/2014.
 *
 * Fragment for the ViewPager that displays the song info
 */
public class SongDetailFragment extends Fragment {

    //index of the song
    private int index;

    //the instance of the song
    private Song curSong;

    /**
     * Override onCreate
     * set menu bar on fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Create the view of the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //get the desired layout
        View rootView = inflater.inflate(R.layout.activity_song_details, null);

        //get the intent of the activity
        Intent intent = getActivity().getIntent();

        //get the position passed to the fragment
        String position = intent.getStringExtra(SongDetailsActivity.POSITION_KEY);

        try{
            //get the index of the song based on position
            index = Integer.parseInt(position);

            //get the song that was selected
            curSong = SongListActivity.songs.get(index);
        }catch (Exception e){

        }

        //Set values for all UI elements based on the curSong

        TextView time = (TextView) rootView.findViewById(R.id.tvTimeSpentDetail);
        time.setText(curSong.getComments().getTimePracticed());

        TextView concert = (TextView) rootView.findViewById(R.id.tvConcertDetail);
        concert.setText(curSong.getComments().getConcert());

        TextView date = (TextView) rootView.findViewById(R.id.tvSongDateDetail);
        date.setText(curSong.getComments().getMonth() + "/"+curSong.getComments().getDay() +
                "/"+ curSong.getComments().getYear());

        TextView notes = (TextView) rootView.findViewById(R.id.tvSongNotesDetail);
        notes.setText(curSong.getComments().getDetails());

        TextView style = (TextView) rootView.findViewById(R.id.tvSongStyleDetail);
        style.setText(curSong.getComments().getTypeOfPiece());

        TextView status = (TextView) rootView.findViewById(R.id.tvSongStatusDetail);
        status.setText(SongComments.StatusEnum.getItem(curSong.getComments().getStatus()).toString());

        TextView title = (TextView) rootView.findViewById(R.id.tvSongTitleDetail);
        title.setText(curSong.getTitle());

        //set the background
        rootView.setBackgroundResource(R.drawable.music_bckgrnd_two);

        return rootView;
    }

    /**
     * Set the actions for each menu item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the item selected
        int id = item.getItemId();

        //if the metronome is selected go to the metronome activity
        if (id == R.id.submenu_metronome) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), MetronomeActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if the reminder option is selected go to the reminder activity
        if (id == R.id.submenu_reminder) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), ReminderActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if the main menu option is selected return to the
        //main menu and clear the backstack
        if (id == R.id.action_main_menu) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), SongListActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            getActivity().finish();
            return true;
        }

        //if the user chooses to remove the song
        //show the dialog to make sure they want to delete it
        if(id ==R.id.action_remove_item){
            RemoveSongDialog fragment = new RemoveSongDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(RemoveSongDialog.KEY_REMOVE, index);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getFragmentManager(), RemoveSongDialog.TAG);
            return true;
        }

        //if the user chooses to edit the section
        //bring up the edit dialog fragment
        if(id == R.id.action_edit_section){
            AddNewSongDialog fragment = new AddNewSongDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(AddNewSongDialog.CREATE_KEY, index);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getFragmentManager(), AddNewSongDialog.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the menu for the menu bar
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_song_details, menu);
    }
}
