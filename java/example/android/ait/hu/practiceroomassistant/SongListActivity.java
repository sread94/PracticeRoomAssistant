package example.android.ait.hu.practiceroomassistant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import example.android.ait.hu.practiceroomassistant.adapter.SongsAdapter;
import example.android.ait.hu.practiceroomassistant.data.PracticeSections;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;
import example.android.ait.hu.practiceroomassistant.data.UserRecordings;
import example.android.ait.hu.practiceroomassistant.data.WebURL;
import example.android.ait.hu.practiceroomassistant.service.NotificationService;

/**
 * Created by Sarah Read on 11/4/2014.
 *
 * Activity for the list of songs
 * Also acts as the main menu/main page
 */
public class SongListActivity extends ListActivity {

    //an array list of songs that the user has created
    public static ArrayList<Song> songs;

    /**
     * Override onCreate
     * initialize values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        startService(new Intent(this, NotificationService.class));
        getActionBar().setTitle("My Songs");

        //if songs list has not yet been created, create it
        if(songs==null) {
            songs = new ArrayList<Song>();
            getSongs();
        }

        //set a list adapter on the activity
        setListAdapter(new SongsAdapter(this));
    }

    /**
     * set the menu for the menu bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
        return true;
    }

    /**
     * on resume update data
     */
    @Override
    protected void onResume() {
        super.onResume();
        sortSongs();
        ((SongsAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /**
     * set actions for each menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get id for the item clicked
        int id = item.getItemId();

        //if metronome option clicked go to metronome activity
        if (id == R.id.submenu_metronome) {
            Intent newIntent = new Intent();
            newIntent.setClass(this, MetronomeActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if reminder option is clicked go to reminder activity
        if (id == R.id.submenu_reminder) {
            Intent newIntent = new Intent();
            newIntent.setClass(this, ReminderActivity.class);
            startActivity(newIntent);
            return true;
        }

        //if add new song option is clicked show dialog so users
        //can create the new song object
        if (id == R.id.action_add_new) {
            AddNewSongDialog fragment = new AddNewSongDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(AddNewSongDialog.CREATE_KEY, -1);
            fragment.setArguments(bundle);
            fragment.show(getFragmentManager(), AddNewSongDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * add a song to the array list songs
     * @param song
     */
    public static void addSong(Song song){
        songs.add(song);
    }

    /**
     * get song data from the database
     */
    public static void getSongs(){

        //get the comments from memory
        List<SongComments> comments = SongComments.listAll(SongComments.class);

        //set songs to a new array list
        songs = new ArrayList<Song>();

        //create the songs
        for(int i = 0; i<comments.size(); i++){
            String songName = comments.get(i).getSong();
            Song newSong = new Song(songName);
            newSong.addComments(comments.get(i));
            songs.add(newSong);
        }

        //get the practice sections from memory
        List<PracticeSections> prSections = PracticeSections.listAll(PracticeSections.class);

        //put the practice sections in the correct songs
        //delete if song title does not match any songs
        for(int j = 0; j<prSections.size(); j++){
            for(int k = 0; k<songs.size(); k++){
                if(songs.get(k).getTitle().equals(prSections.get(j).getSong())){
                    songs.get(k).addPracticeSections(prSections.get(j));
                    break;
                }
                else if(k==songs.size()-1){
                    prSections.get(j).delete();
                }
            }
        }

        //get the web links from memory
        List<WebURL> urls = WebURL.listAll(WebURL.class);

        //put the web links in the correct songs
        //delete if song title does not match any songs
        for(int j = 0; j<urls.size(); j++){
            for(int k = 0; k<songs.size(); k++){
                if(songs.get(k).getTitle().equals(urls.get(j).getSong())){
                    songs.get(k).addUrl(urls.get(j));
                    break;
                }
                else if(k==songs.size()-1){
                    urls.get(j).delete();
                }
            }
        }

        //get the recordings from memory
        List<UserRecordings> recordings = UserRecordings.listAll(UserRecordings.class);

        //put the recordings in the correct song
        //delete if song title does not match any songs
        for(int j = 0; j<recordings.size(); j++){
            for(int k = 0; k<songs.size(); k++){
                if(songs.get(k).getTitle().equals(recordings.get(j).getSong())){
                    songs.get(k).addRecording(recordings.get(j));
                    break;
                }
                else if(k==songs.size()-1){
                    recordings.get(j).delete();
                }
            }
        }

    }

    /**
     * sort songs based on status
     */
    public static void sortSongs(){

        //create a list for each status option
        ArrayList<Song> songs0 = new ArrayList<Song>();
        ArrayList<Song> songs1 = new ArrayList<Song>();
        ArrayList<Song> songs2 = new ArrayList<Song>();

        //put each song in the correct status list
        for(int i=0; i<songs.size(); i++){
            int songStat = songs.get(i).getComments().getStatus();
            if(songStat ==0){
                songs0.add(songs.get(i));
            }
            else if(songStat ==1){
                songs1.add(songs.get(i));
            }
            else{
                songs2.add(songs.get(i));
            }
        }

        //sort each status group alphabetically
        if(songs0.size()>1) {
            songs0 = sortStatus(songs0, 0);
        }
        if(songs1.size()>1) {
            songs1 = sortStatus(songs1, 0);
        }
        if(songs2.size()>1) {
            songs2 = sortStatus(songs2, 0);
        }

        //put the 3 status lists into 1 list
        songs= new ArrayList<Song>();
        for(int i = 0; i<songs0.size(); i++){
            songs.add(songs0.get(i));
        }
        for(int i = 0; i<songs1.size(); i++){
            songs.add(songs1.get(i));
        }
        for(int i = 0; i<songs2.size(); i++){
            songs.add(songs2.get(i));
        }
    }

    /**
     * Put the list of songs in alphabetical order
     * @param list
     * @param index
     * @return
     */
    private static ArrayList<Song> sortStatus(ArrayList<Song> list, int index){
        Song first = list.get(index);
        for(int i = index+1; i<list.size(); i++){
            Song tempSong = list.get(i);
            if(first.getTitle().compareTo(tempSong.getTitle())>0){
                list.remove(i);
                list.add(index,tempSong);
                first = tempSong;
            }
        }
        if(list.size()-2 > index){
            return sortStatus(list, index+1);
        }
        return list;
    }

}
