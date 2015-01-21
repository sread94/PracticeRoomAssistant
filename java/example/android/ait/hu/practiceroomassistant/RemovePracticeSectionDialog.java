package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Sarah Read on 11/29/2014.
 *
 * Dialog asking the user if they want to delete the
 * selected practice section
 */
public class RemovePracticeSectionDialog extends DialogFragment implements DialogInterface.OnClickListener {

    //Tag to show dialog
    public static final String TAG = "removeSectionTag";

    //key to get the index of the song
    public static final String KEY_REMOVE_SONG = "songWithSection";

    //key to get index of the practice section
    public static final String KEY_REMOVE_SECTION = "sectionToRemove";

    //index of the song in the song list
    private int indexSong;

    //index of the practice section being deleted
    private int indexSection;

    /**
     * Create the dialog
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get the index of the practice section being deleted
        indexSection = getArguments().getInt(KEY_REMOVE_SECTION);

        //get the index of the song the practice sections belongs to
        indexSong = getArguments().getInt(KEY_REMOVE_SONG);

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete a Section");

        //set message
        builder.setMessage("Do you want to delete this practice section?");

        //if user clicks yes delete the section
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //SongListActivity.songs.remove(index);
                SongListActivity.songs.get(indexSong).getPracticeSections().get(indexSection)
                    .delete();

                SongListActivity.getSongs();
                Intent intent = getActivity().getIntent();
                intent.putExtra(SongDetailsActivity.START_SCREEN, 1 + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivityForResult(intent, 10);
                dialog.dismiss();
            }
        });

        //cancel if the user clicks no
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
