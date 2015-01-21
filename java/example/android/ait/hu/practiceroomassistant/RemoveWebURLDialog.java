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
 * Dialog Fragment for Removing web links
 */
public class RemoveWebURLDialog extends DialogFragment implements Dialog.OnClickListener {

    //Tag to show dialog
    public static final String TAG = "removingWebTag";

    //key to get the index of the song that contains the URL
    public static final String KEY_REMOVE = "positionToRemoveSong";

    //key to get the index of the url
    public static final String KEY_URL_INDEX = "thisistheindexofurl";

    //index of the song
    private int index;

    //index of the url
    private int urlIndex;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get the index of the song
        index = getArguments().getInt(KEY_REMOVE);

        //get the index of the url
        urlIndex = getArguments().getInt(KEY_URL_INDEX);

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete a Link");

        builder.setMessage("Do you want to delete this link?");

        //When the user presses "Yes" remove the selected web link
        //reset the web links fragment and dismiss the dialog
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SongListActivity.songs.get(index).getUrls().get(urlIndex).delete();

                SongListActivity.getSongs();
                Intent intent = getActivity().getIntent();
                intent.putExtra(SongDetailsActivity.START_SCREEN, 3 + "");
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
