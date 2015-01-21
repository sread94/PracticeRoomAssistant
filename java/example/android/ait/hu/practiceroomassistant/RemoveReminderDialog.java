package example.android.ait.hu.practiceroomassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Sarah Read on 11/30/2014.
 *
 * Dialog Fragment to remove a reminder
 */
public class RemoveReminderDialog extends DialogFragment implements DialogInterface.OnClickListener {

    //tag to show show the dialog
    public static final String TAG = "removeReminderTag";

    //key to get the index of the reminder being removed
    public static final String KEY_REMOVE = "removeThisReminder";

    //index of the reminder being removed
    private int index;

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get the index of the reminder
        index = getArguments().getInt(KEY_REMOVE);

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete a Reminder");

        builder.setMessage("Do you want to delete this reminder?");

        //When the user presses "Yes" remove the reminder
        //Update the reminder activity and dismiss the dialog
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //SongListActivity.songs.remove(index);
                ReminderActivity.reminders.get(index).delete();

                ReminderActivity.getSavedReminders();
                Intent intent = getActivity().getIntent();
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
