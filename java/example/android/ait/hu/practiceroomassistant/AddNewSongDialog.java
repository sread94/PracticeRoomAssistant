package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import example.android.ait.hu.practiceroomassistant.data.PracticeSections;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;
import example.android.ait.hu.practiceroomassistant.data.UserRecordings;
import example.android.ait.hu.practiceroomassistant.data.WebURL;

/**
 * Created by Sarah Read on 11/18/2014.
 *
 * Creates a Dialog Fragment for adding and editing song details
 */
public class AddNewSongDialog extends DialogFragment implements
        DialogInterface.OnClickListener{

    //tag for the fragment
    public static final String TAG = "TagForAddingASong";

    //key for getting the index of the song
    public static final String CREATE_KEY = "createSongKey";

    //index of the song if it exists (-1 if the song is new)
    private int index;

    /**
     * create the dialog
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        index = getArguments().getInt(CREATE_KEY);

        //set title based on index
        if(index>-1){
            builder.setTitle("Edit Song Details");
        }
        else{
            builder.setTitle("Create New Song");
        }

        //add xml view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_new_song, null);

        //get a reference to all of the edit texts
        final EditText concert = (EditText) layout.findViewById(R.id.etConcert);
        final EditText title = (EditText) layout.findViewById(R.id.etSongTitle);
        final EditText style = (EditText) layout.findViewById(R.id.etStyle);
        final EditText details = (EditText) layout.findViewById(R.id.etNotes);

        //create array for the day spinner
        String[] dayArr = new String[31];
        for(int i = 0; i<dayArr.length; i++){
            int day  = i+1;
            dayArr[i]=day+"";
        }

        //create day spinner
        final Spinner day = (Spinner) layout.findViewById(R.id.spDay);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dayArr);
        day.setAdapter(adapterDay);

        //create array for the month spinner
        String[] monthArr = new String[12];
        for(int i = 0; i<monthArr.length; i++){
            int month = i+1;
            monthArr[i]=month+"";
        }

        //create the month spinner
        final Spinner month = (Spinner) layout.findViewById(R.id.spMonth);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, monthArr);
        month.setAdapter(adapterMonth);

        //create the year array for the year spinner
        String[] yearArr = new String[20];
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = curYear; i<yearArr.length+curYear; i++){
            yearArr[i-curYear]=i+"";
        }

        //create the year spinner
        final Spinner year = (Spinner) layout.findViewById(R.id.spYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, yearArr);
        year.setAdapter(adapterYear);

        //create the status array for the status spinner
        String[] statusArr = new String[3];
        for(int i = 0; i<statusArr.length; i++){
            statusArr[i] = SongComments.StatusEnum.getItem(i).toString();
        }

        //create the status spinner
        final Spinner status = (Spinner) layout.findViewById(R.id.spStatus);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, statusArr);
        status.setAdapter(adapterStatus);

        //create the time array for the time spinner
        String[] timeArr = new String[40];
        for(int i = 0; i<timeArr.length; i++){
            int hours = i/2;
            String half = "";
            if(i%2==1){
                half = ".5";
            }
            timeArr[i]=hours+half + " hours";
        }

        //create the time spinner (for time spent)
        final Spinner time = (Spinner) layout.findViewById(R.id.spTimeSpent);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, timeArr);
        time.setAdapter(adapterTime);

        //set values of UI elements if the song already exists
        if(index>-1){
            Song curSong = SongListActivity.songs.get(index);
            concert.setText(curSong.getComments().getConcert());
            style.setText(curSong.getComments().getTypeOfPiece());
            details.setText(curSong.getComments().getDetails());
            title.setText(curSong.getTitle());

            status.setSelection(curSong.getComments().getStatus());
            day.setSelection(curSong.getComments().getDay()-1);
            month.setSelection(curSong.getComments().getMonth() - 1);
            year.setSelection(curSong.getComments().getYear()-2014);

            String timeSpentString = curSong.getComments().getTimePracticed();
            double timeSpent = Double.parseDouble(timeSpentString.substring(0, timeSpentString.indexOf(" ")));
            int timeInt = (int)timeSpent;
            if(timeSpent>timeInt){
                time.setSelection(2*timeInt+1);
            }
            else{
                time.setSelection(2*timeInt);
            }
        }

        //set the view
        builder.setView(layout);

        //set buttons if the song is new
        if(index ==-1) {

            //when the user clicks the add button get all of the entered values
            //and create a song
            //Save the entered values as a SongComments
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SongComments comments = new SongComments(title.getText().toString());
                    comments.setConcert(concert.getText().toString());
                    comments.setTypeOfPiece(style.getText().toString());
                    comments.setTimePracticed(time.getSelectedItem().toString());
                    comments.setStatus(status.getSelectedItemPosition());
                    comments.setDay(Integer.parseInt(day.getSelectedItem().toString()));
                    comments.setMonth(Integer.parseInt(month.getSelectedItem().toString()));
                    comments.setYear(Integer.parseInt(year.getSelectedItem().toString()));
                    comments.setDetails(details.getText().toString());
                    comments.setSong(title.getText().toString());

                    int numRepeat = findNumRepeat(title.getText().toString(),0);
                    if(numRepeat>0){
                        String newTitle = title.getText().toString() + " (" + numRepeat + ")";
                        comments.setSong(newTitle);
                    }

                    comments.save();

                    SongListActivity.getSongs();

                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    getActivity().startActivityForResult(intent, 10);

                    dialog.dismiss();
                }
            });
        }

        //set buttons if the song exists
        else{

            //create a new SongComments with the new values
            builder.setPositiveButton("Make Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SongComments comments = new SongComments(title.getText().toString());
                    comments.setConcert(concert.getText().toString());
                    comments.setTypeOfPiece(style.getText().toString());
                    comments.setTimePracticed(time.getSelectedItem().toString());
                    comments.setStatus(status.getSelectedItemPosition());
                    comments.setDay(Integer.parseInt(day.getSelectedItem().toString()));
                    comments.setMonth(Integer.parseInt(month.getSelectedItem().toString()));
                    comments.setYear(Integer.parseInt(year.getSelectedItem().toString()));
                    comments.setDetails(details.getText().toString());
                    comments.setSong(title.getText().toString());

                    //if the title has changed find the index of the song and then
                    //change the title and set the comments
                    if(!title.getText().toString().equals(SongListActivity.songs.get(index).getTitle())){
                        int numRepeat = findNumRepeat(title.getText().toString(),0);
                        String newTitle = title.getText().toString();
                        if(numRepeat>0) {
                            newTitle += " (" + numRepeat + ")";
                        }
                            comments.setSong(newTitle);

                            //reset song titles in the practice sections
                            ArrayList<PracticeSections> prArray = SongListActivity.songs.get(index).getPracticeSections();

                            for(int i = 0; i<prArray.size(); i++) {
                                PracticeSections sections = prArray.get(i);
                                sections.setSong(newTitle);
                                sections.save();
                            }

                            //reset song titles in the web urls
                            ArrayList<WebURL> urlArray = SongListActivity.songs.get(index).getUrls();

                            for(int i = 0; i<urlArray.size(); i++){
                                WebURL curURL = urlArray.get(i);
                                curURL.setSong(newTitle);
                                curURL.save();
                            }

                            //reset song titles in the user recordings
                            ArrayList<UserRecordings> reArray = SongListActivity.songs.get(index).getRecordings();

                            for(int i = 0; i<reArray.size(); i++){
                                UserRecordings curRe = reArray.get(i);
                                curRe.setSong(newTitle);
                                curRe.save();
                            }

                    }

                    comments.save();

                    //delete previous comments
                    Song getSong = SongListActivity.songs.get(index);
                    getSong.getComments().delete();
                    SongListActivity.getSongs();

                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(SongDetailsActivity.START_SCREEN, 0 + "");
                    intent.putExtra(SongDetailsActivity.POSITION_KEY,getSongIndex(title.getText().toString())+"");
                    getActivity().startActivityForResult(intent, 10);

                    dialog.dismiss();
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    /**
     * If the title is changed to an existing song title
     * add a (#) to the end of it
     *
     * ex:
     * Song 1: My Song
     * Song 2: My Song (1) (if changed to My Song)
     * Song 3: My Song (2) (if changed to My Song)
     * @param songTitle
     * @param repeat
     * @return
     */
    private int findNumRepeat(String songTitle, int repeat){
        String compareTitle = songTitle;
        if(repeat>0){
            compareTitle = compareTitle + " ("+repeat+")";

        }
        for(int i = 0; i<SongListActivity.songs.size();i++){
            if(compareTitle.equals(SongListActivity.songs.get(i).getTitle())){
                return findNumRepeat(songTitle, repeat+1);
            }
        }
        return repeat;
    }

    /**
     * get the index of the song in the list
     * SongListActivity.songs
     *
     * @param title
     * @return
     */
    private int getSongIndex(String title){

        //sort the songs
        SongListActivity.sortSongs();
        List<Song> songList = SongListActivity.songs;

        //find the song that matches the title given
        for(int i = 0; i<songList.size(); i++){
            if(songList.get(i).getTitle().equals(title)){
                return i;
            }
        }

        return -1;
    }
}
