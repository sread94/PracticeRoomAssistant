package example.android.ait.hu.practiceroomassistant;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import example.android.ait.hu.practiceroomassistant.adapter.PracticeSectionAdapter;

/**
 * Created by Sarah Read on 11/24/2014.
 *
 * List Fragment for song sections (in ViewPager)
 */
public class SongSectionsFragment extends ListFragment {

    //the index of the song the user is on
    private int index;

    /**
     * initialize values
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        index = 0;
        if (getArguments().containsKey(SongDetailsActivity.POSITION_KEY)) {

            //get the index of the song
            int position = Integer.parseInt(getArguments().getString(SongDetailsActivity.POSITION_KEY));
            index = position;
        }

        //set menu bar
        setHasOptionsMenu(true);

        this.setListAdapter(new PracticeSectionAdapter(getActivity(), index));
    }

    /**
     * create the view by setting layout
     * and background
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
     * notify that data has changed
     */
    @Override
    public void onResume() {
        super.onResume();
        ((PracticeSectionAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * Define actions for each menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the id of the item selected
        int id = item.getItemId();

        //if the metronome option is clicked go to the
        //metronome activity
        if (id == R.id.submenu_metronome) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), MetronomeActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if the reminder option is clicked go to the
        //reminder activity
        if (id == R.id.submenu_reminder) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), ReminderActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if the user selects the main menu option
        //go to the main menu (SongListActivity)
        //and clear the back stack
        if (id == R.id.action_main_menu) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), SongListActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            getActivity().finish();
            return true;
        }

        //When the user selects remove song
        //show the dialog to ask if user wants to delete song
        if (id == R.id.action_remove_item) {
            RemoveSongDialog fragment = new RemoveSongDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(RemoveSongDialog.KEY_REMOVE, index);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getFragmentManager(), RemoveSongDialog.TAG);
            return true;
        }

        //if the user selects add section option
        //open a dialog to create a dialog
        if (id == R.id.action_add_section) {
            AddPracticeSectionDialog fragment = new AddPracticeSectionDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(AddPracticeSectionDialog.CREATE_PRSECTION_KEY, index);
            bundle.putInt(AddPracticeSectionDialog.NEW_PRSECT, -1);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getFragmentManager(), AddPracticeSectionDialog.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the menu bar
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_song_sections, menu);
    }

}

