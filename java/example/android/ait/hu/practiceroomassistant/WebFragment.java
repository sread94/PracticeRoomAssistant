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

import example.android.ait.hu.practiceroomassistant.adapter.WebAdapter;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * List Fragment for the list of web links
 */
public class WebFragment extends ListFragment {

    //index of the song the user is currently on
    private int index;

    //Code to request a return value
    public static final int REQUEST_CODE_URL = 123;

    /**
     * Initialize values
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the index
        if (getArguments().containsKey(SongDetailsActivity.POSITION_KEY)) {
            int position = Integer.parseInt(getArguments().getString(SongDetailsActivity.POSITION_KEY));
            index = position;
        }

        //enable menu bar
        setHasOptionsMenu(true);

        //set list adapter
        this.setListAdapter(new WebAdapter(getActivity(), index));
    }

    /**
     * create the view by setting the layout
     * and the background
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
     * notify data is changed
     */
    @Override
    public void onResume() {
        super.onResume();
        ((WebAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * define the actions for each of the menu options
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the id of the item clicked
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

        //if the user selects the add web link option
        //go to the ChooseWebURL activity
        if (id == R.id.action_add_section) {
            Intent newIntent = new Intent();
            newIntent.setClass(getActivity(), ChooseWebURL.class);
            startActivityForResult(newIntent, REQUEST_CODE_URL);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * set the menu bar
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_web_list, menu);
    }


    /**
     * Set actions for when a value is returned
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the request code is correct
        if(requestCode == REQUEST_CODE_URL){
            String theURL = "";

            //get the string returned.
            try {
                theURL = data.getStringExtra(ChooseWebURL.KEY_URL);
            }catch(Exception e) {
                return;
            }

            //if the url is not blank show a dialog to define
            //a title of the url
            if(!theURL.equals("")){
                AddNewWebURLDialog fragment = new AddNewWebURLDialog();
                Bundle bundle = new Bundle();
                bundle.putString(AddNewWebURLDialog.KEY_URL, theURL);
                bundle.putInt(AddNewWebURLDialog.KEY_SONG_INDEX, index);
                fragment.setArguments(bundle);
                fragment.show(getActivity().getFragmentManager(), AddNewWebURLDialog.TAG);
            }
        }
    }
}
