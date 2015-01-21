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

import example.android.ait.hu.practiceroomassistant.data.PracticeSections;
import example.android.ait.hu.practiceroomassistant.data.Song;
import example.android.ait.hu.practiceroomassistant.data.SongComments;

/**
 * Created by Sarah Read on 11/19/2014.
 *
 * Dialog Fragment to add a practice section to a song
 */
public class AddPracticeSectionDialog extends DialogFragment implements DialogInterface.OnClickListener {

    //tag for the fragment
    public static final String TAG = "TagForAddingPracticeSection";

    //key to get the index of the song
    public static final String CREATE_PRSECTION_KEY = "createSongKey";

    //key to get the practice section index that was clicked (-1 if it is a new practice section)
    public static final String NEW_PRSECT = "isANewSection";

    //index of the song
    private int index;

    //index of the practice section
    private int sectionClicked;

    /**
     * creates the dialog fragment for adding/editing a practice section
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        sectionClicked = getArguments().getInt(NEW_PRSECT);
        index = getArguments().getInt(CREATE_PRSECTION_KEY);

        if(sectionClicked>-1){
            builder.setTitle("Edit Practice Section");
        }
        else{
            builder.setTitle("Create New Practice Section");
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_new_practice, null);

        //get a references to the UI elements
        final EditText notes = (EditText) layout.findViewById(R.id.etNotesSection);
        final EditText type = (EditText) layout.findViewById(R.id.etTypeSection);
        final EditText section = (EditText) layout.findViewById(R.id.etPrSection);

        //status array for the status spinner
        String[] statusArr = new String[3];
        for(int i = 0; i<statusArr.length; i++){
            statusArr[i] = SongComments.StatusEnum.getItem(i).toString();
        }

        //create the status spinner
        final Spinner status = (Spinner) layout.findViewById(R.id.spStatusSection);
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

        //create the spinner for time spent
        final Spinner time = (Spinner) layout.findViewById(R.id.spTimeSpentSection);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, timeArr);
        time.setAdapter(adapterTime);

        //if the practice sections is being edited set the proper values of the UI elements
        if(sectionClicked>-1){
            PracticeSections clickedSection = SongListActivity.songs.get(index).getPracticeSections().get(sectionClicked);
            section.setText(clickedSection.getSection());
            type.setText(clickedSection.getTypeOfSection());
            notes.setText(clickedSection.getNotesOnSection());

            String timeSpentString = clickedSection.getTimePracticed();
            double timeSpent = Double.parseDouble(timeSpentString.substring(0, timeSpentString.indexOf(" ")));
            int timeInt = (int)timeSpent;
            if(timeSpent>timeInt){
                time.setSelection(2*timeInt+1);
            }
            else{
                time.setSelection(2*timeInt);
            }

            status.setSelection(clickedSection.getStatus());
        }

        //if creating a new section set an add button
        if(sectionClicked==-1) {

            //create a new PracticeSections and save it
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PracticeSections newSection = new PracticeSections();
                    newSection.setSong(SongListActivity.songs.get(index).getTitle());
                    newSection.setSection(section.getText().toString());
                    newSection.setTypeOfSection(type.getText().toString());
                    newSection.setNotesOnSection(notes.getText().toString());
                    newSection.setTimePracticed(time.getSelectedItem().toString());
                    newSection.setStatus(status.getSelectedItemPosition());

                    newSection.save();
                    SongListActivity.getSongs();
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(SongDetailsActivity.START_SCREEN, 1 + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivityForResult(intent, 10);
                    dialog.dismiss();
                }
            });
        }

        //if editing set a "Make Changes" button
        else {

            //create a new PracticeSection save it and delete the previous one
            builder.setPositiveButton("Make Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PracticeSections newSection = new PracticeSections();
                    newSection.setSong(SongListActivity.songs.get(index).getTitle());
                    newSection.setSection(section.getText().toString());
                    newSection.setTypeOfSection(type.getText().toString());
                    newSection.setNotesOnSection(notes.getText().toString());
                    newSection.setTimePracticed(time.getSelectedItem().toString());
                    newSection.setStatus(status.getSelectedItemPosition());

                    newSection.save();

                    Song theSong = SongListActivity.songs.get(index);
                    PracticeSections theSection = theSong.getPracticeSections().get(sectionClicked);
                    theSection.delete();

                    SongListActivity.getSongs();
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(SongDetailsActivity.START_SCREEN, 1 + "");
                    getActivity().startActivityForResult(intent, 10);
                    dialog.dismiss();
                }
            });
        }

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setView(layout);

        return builder.create();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
