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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import example.android.ait.hu.practiceroomassistant.data.Reminder;

/**
 * Created by Sarah Read on 11/29/2014.
 *
 * Dialog Fragment for adding a reminder
 */
public class AddReminderDialog extends DialogFragment implements DialogInterface.OnClickListener{

    //tag
    public static final String TAG = "TagForAddingReminder";

    //key to get the index of the reminder being edited
    public static final String INDEX_KEY = "isTheItemEdited";

    //index of the reminder (-1 if it is new)
    private int index;

    /**
     * create the dialog to add/edit a reminder
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //build the fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Notification");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_reminder, null);

        index = getArguments().getInt(INDEX_KEY);

        if(index>-1){
            builder.setTitle("Edit Notification");
        }
        else{
            builder.setTitle("Create New Notification");
        }

        //get references to the UI elements
        final CheckBox repeat = (CheckBox) layout.findViewById(R.id.cbRepeatReminder);
        final EditText message = (EditText) layout.findViewById(R.id.etMessageReminder);

        //create array of minutes for the minute spinner
        String[] minArr = new String[60];
        for(int i= 0; i<minArr.length; i++){
            String minString = i +"";
            if(i<10){
                minString = "0"+ minString;
            }
            minArr[i]=minString;
        }

        //create the minute spinner
        final Spinner min = (Spinner) layout.findViewById(R.id.spMinReminder);
        ArrayAdapter<String> adapterMin = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, minArr);
        min.setAdapter(adapterMin);

        //create the hour array for the hour spinner
        String[] hourArr = new String[24];
        for(int i = 0; i<hourArr.length; i++){
            String hourString = i+"";
            if(i<10){
                hourString = "0"+hourString;
            }
            hourArr[i]=hourString;
        }

        //create the hour spinner
        final Spinner hour = (Spinner) layout.findViewById(R.id.spHourReminder);
        ArrayAdapter<String> adapterHour = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, hourArr);
        hour.setAdapter(adapterHour);

        //create the day array
        String[] dayArr = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        //create the day spinner
        final Spinner day = (Spinner) layout.findViewById(R.id.spDayReminder);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dayArr);
        day.setAdapter(adapterDay);

        //if the reminder is new
        if(index<0) {

            //when the user clicks add create a new reminder given the
            //user entered values
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Reminder newReminder = new Reminder(hour.getSelectedItemPosition(),
                            min.getSelectedItemPosition(), day.getSelectedItemPosition(),
                            message.getText().toString(), repeat.isChecked());

                    newReminder.save();
                    ReminderActivity.getSavedReminders();
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivityForResult(intent, 10);
                    dialog.dismiss();
                }
            });
        }

        //if the reminder is being edited
        else{

            //set the UI elements accordingly
            Reminder curReminder = ReminderActivity.reminders.get(index);
            repeat.setChecked(curReminder.isRepeat());
            message.setText(curReminder.getMessage());
            day.setSelection(curReminder.getDay());
            min.setSelection(curReminder.getStartMin());
            hour.setSelection(curReminder.getStartHour());

            //create a new reminder when the user clicks makes changes
            //and delete the previous one
            builder.setPositiveButton("Make Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Reminder newReminder = new Reminder(hour.getSelectedItemPosition(),
                            min.getSelectedItemPosition(), day.getSelectedItemPosition(),
                            message.getText().toString(), repeat.isChecked());

                    ReminderActivity.reminders.get(index).delete();
                    newReminder.save();

                    ReminderActivity.getSavedReminders();
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
