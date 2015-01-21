package example.android.ait.hu.practiceroomassistant;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


/**
 * Created by Sarah Read on 12/4/2014.
 */
public class CreateNewRecordingActivity extends Activity {

    //key for the file
    public static final String KEY_RECORDING = "RecordingKey";

    //key for the file label
    public static final String KEY_RECORDING_LABEL = "RecordingLabelKey";

    //the file path
    private String mFileName = "";

    //the file label
    private String fileLabel = "";

    //Media Player
    private MediaPlayer mPlayer;

    //Media Recorder
    private MediaRecorder mRecorder;

    //Reference to the TextView for the recording label
    private TextView tvRecordingLabel;

    //reference to the EditText for the recording label
    private EditText etRecordingName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recording_layout);
        Intent i = getIntent();
        setViews(i);

    }

    /**
     * Set up the view
     *
     * Get references to all UI elements
     * Set button actions
     * @param intent
     */
    private void setViews(Intent intent) {
        final Intent i = intent;

        //get references to UI elements
        tvRecordingLabel = (TextView) findViewById(R.id.tvRecordingLabel);
        Button startRecButton = (Button) findViewById(R.id.startRecordingButton);
        Button stopRecButton = (Button) findViewById(R.id.stopRecordingButton);
        Button startPlayButton = (Button) findViewById(R.id.startPlayingButton);
        Button stopPlayButton = (Button) findViewById(R.id.stopPlayingButton);
        Button cancel = (Button) findViewById(R.id.btnCancelRecord);
        Button save = (Button) findViewById(R.id.btnSubmitRecord);
        etRecordingName = (EditText) findViewById(R.id.etRecordingName);

        //when the cancel button is clicked no recording is linked
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(KEY_RECORDING, "");
                i.putExtra(KEY_RECORDING_LABEL,"");
                setResult(RecordingsFragment.REQUEST_CODE_RECORD, i);
                finish();
            }
        });

        //when the save button is clicked link to the recording
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(KEY_RECORDING, mFileName);
                i.putExtra(KEY_RECORDING_LABEL, fileLabel);
                setResult(RecordingsFragment.REQUEST_CODE_RECORD, i);
                finish();
            }
        });

        //start recording
        startRecButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        //stop recording
        stopRecButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        //start playing
        startPlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });

        //stop playing
        stopPlayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
            }
        });

    }

    /**
     * start playing the file (if it exists)
     */
    private void startPlaying() {
        mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
                tvRecordingLabel.setText("Playing");
            } catch (IOException e) {

            }
    }

    /**
     * stop playing the recording
     */
    private void stopPlaying() {
        try {
            mPlayer.release();
            mPlayer = null;
            tvRecordingLabel.setText("");
        }catch(Exception e){

        }
    }

    /**
     * start recording audio
     */
    private void startRecording() {
        //record only if there is a file name
        if(!etRecordingName.getText().toString().equals("")) {
            mFileName =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/" +etRecordingName.getText().toString() + ".3gp";
            fileLabel = etRecordingName.getText().toString();
            try {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(
                        MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(
                        MediaRecorder.OutputFormat.THREE_GPP);
                File outputFile = new File(mFileName);
                if (outputFile.exists())
                    outputFile.delete();
                outputFile.createNewFile();
                mRecorder.setOutputFile(mFileName);
                mRecorder.setAudioEncoder(
                        MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.prepare();
                mRecorder.start();
                tvRecordingLabel.setText("Recording...");
            } catch (IOException e) {

            }
        }
        else{
            tvRecordingLabel.setText("Cannot record. No file name entered.");
        }
    }

    /**
     * stop the recording
     */
    private void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            tvRecordingLabel.setText("");
        }catch (Exception e){

        }
    }


}
