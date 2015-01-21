package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import example.android.ait.hu.practiceroomassistant.data.WebURL;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * Dialog fragment to add a new WebURL to a song
 */
public class AddNewWebURLDialog extends DialogFragment  implements
        DialogInterface.OnClickListener {

    //tag for the fragment
    public static final String TAG = "AddNewWebURLTag";

    //key to get the song index
    public static final String KEY_SONG_INDEX = "createSongKey";

    //key to get the url
    public static final String KEY_URL = "the url is...";

    //index of the song
    private int index;

    /**
     * Create the dialog for to user to label the link
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a Song");

        index = getArguments().getInt(KEY_SONG_INDEX);
        String url = getArguments().getString(KEY_URL);

        //add xml view
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_new_url, null);

        //get reference to the UI elements
        final EditText etPageLabel = (EditText) layout.findViewById(R.id.etPageLabel);
        final TextView tvURL = (TextView) layout.findViewById(R.id.tvURL);
        tvURL.setText(url);

        builder.setView(layout);

        //add a positive button that when clicked
        //creates and saves a WebURL
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WebURL newURL = new WebURL(tvURL.getText().toString(),
                            SongListActivity.songs.get(index).getTitle(),
                            etPageLabel.getText().toString());
                    newURL.save();
                    SongListActivity.getSongs();

                    Intent intent = getActivity().getIntent();
                    intent.putExtra(SongDetailsActivity.START_SCREEN, 3 + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivityForResult(intent, 10);

                dialog.dismiss();
            }
            });

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
