package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * Dialog Fragment to delete a recording
 */
public class RemoveRecordingDialog extends DialogFragment implements Dialog.OnClickListener {

    //tag to show dialog
    public static final String TAG = "removingRecordingTag";

    //key to get the index of the song
    public static final String KEY_REMOVE = "positionOfSong";

    //key to get the index of the recording
    public static final String KEY_RECORDING_INDEX = "thisistheindexoftherecording";

    //the index of the song
    private int index;

    //the index of the recording
    private int recordIndex;

    /**
     * Create the dialog
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        index = getArguments().getInt(KEY_REMOVE);
        recordIndex = getArguments().getInt(KEY_RECORDING_INDEX);

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete a Recording");

        builder.setMessage("Do you want to delete this recording?");

        //delete the recording if the user selects yet
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SongListActivity.songs.get(index).getRecordings().get(recordIndex).delete();

                SongListActivity.getSongs();
                Intent intent = getActivity().getIntent();
                intent.putExtra(SongDetailsActivity.START_SCREEN, 2 + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivityForResult(intent, 10);
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
