package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;

import example.android.ait.hu.practiceroomassistant.data.WebURL;

/**
 * Created by Sarah Read on 12/5/2014.
 *
 * Dialog Fragment for playing a given recording
 */
public class PlayRecordingDialog extends DialogFragment implements DialogInterface.OnClickListener {

    //key to get the file path of the recording
    public static final String KEY_RECORDING = "the key for the recording";

    //file path of the recording
    private String recordingLocation = "";

    //media player
    private  MediaPlayer mPlayer;

    /**
     * Create the dialog for to play the recording
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a Song");

        //get the file
        recordingLocation = getArguments().getString(KEY_RECORDING);

        //set a button to play the recording
        builder.setPositiveButton("Play Recording", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(recordingLocation);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {

                }
            }
        });

        //sets a button to exit the dialog
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
}
