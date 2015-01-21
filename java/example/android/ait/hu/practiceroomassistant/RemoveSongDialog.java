package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import example.android.ait.hu.practiceroomassistant.data.PracticeSections;

/**
 * Created by Sarah Read on 11/19/2014.
 *
 * Dialog Fragment for removing a song
 */
public class RemoveSongDialog extends DialogFragment implements DialogInterface.OnClickListener {

    //tag to show the dialog
    public static final String TAG = "removeSongTag";

    //key to get the index of the song to remove
    public static final String KEY_REMOVE = "positionToRemoveSong";

    //index of the song
    private int index;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get the index of the song being removed
        index = getArguments().getInt(KEY_REMOVE);

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete a Song");

        builder.setMessage("Do you want to delete this song?");

        //when the user presses "Yes" remove the song
        //Update the song list activity and dismiss the dialog
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SongListActivity.songs.get(index).getComments().delete();
                ArrayList<PracticeSections> sections = SongListActivity.songs.get(index).getPracticeSections();
                SongListActivity.getSongs();
                Intent newIntent = new Intent();
                newIntent.setClass(getActivity(), SongListActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newIntent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
