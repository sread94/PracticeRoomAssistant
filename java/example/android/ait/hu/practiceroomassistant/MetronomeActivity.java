package example.android.ait.hu.practiceroomassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity that acts as a metronome
 */
public class MetronomeActivity extends Activity {

    //the tempo
    private int tempo;

    //number of beats in a measure
    private int beats;

    //true if the metronome is playing
    private boolean metronomeRunning;

    //a MediaPlayer
    private MediaPlayer mp;

    //the context
    private Context thisContext;

    //a Handler
    private Handler myMetronomeHandler;

    //current beat in the measure
    private int beatNum;

    //reference to the text view that displays the beat number
    private TextView tvBeatNumber;

    /**
     * Runnable plays a beat every 60 seconds/tempo
     */
    private Runnable startMetronome = new Runnable() {
        @Override public void run() {
            long tempoLong = tempo;
            myMetronomeHandler.postDelayed(this, 60000/tempoLong);
            int beatText = beatNum+1;
            tvBeatNumber.setText(beatText + "");

            //on the down beat the beat number is red
            //else it is black
            if(beatNum==0){
                tvBeatNumber.setTextColor(Color.RED);
            }
            else{
                tvBeatNumber.setTextColor(Color.BLACK);
            }

            //if the mp is playing an audio file release it
            //before playing another one
            if (mp != null) {
                mp.release();
            }
            mp = MediaPlayer.create(thisContext, R.raw.metronome);
            mp.start();
            beatNum = (beatNum + 1)%beats;
        }
    };

    /**
     * Initialize values and get references to UI elements
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);
        tempo = 140;
        beats = 4;
        metronomeRunning = false;
        thisContext=this;

        //get a reference to the text views and set their values
        final TextView tvTempoVal = (TextView) findViewById(R.id.tvTempoVal);
        final TextView tvBeatsVal = (TextView) findViewById(R.id.tvBeatsVal);
        tvTempoVal.setText(tempo + "");
        tvBeatsVal.setText(beats + "");

        tvBeatNumber = (TextView) findViewById(R.id.tvBeatNumber);

        Button btnBeatsPlus = (Button) findViewById(R.id.btnBeatsPlus);
        Button btnBeatsMinus = (Button) findViewById(R.id.btnBeatsMinus);

        Button btnTempoPlus = (Button) findViewById(R.id.btnTempoPlus);
        Button btnTempoMinus = (Button) findViewById(R.id.btnTempoMinus);

        final Button btnStartMetronome = (Button) findViewById(R.id.btnStartMetronome);

        //when the plus tempo button is touched increase by 1 (to a max of 240)
        //if held down keep adding 1
        btnTempoPlus.setOnTouchListener(new View.OnTouchListener() {

            private Handler myHandler;

            @Override
            public boolean onTouch(View v, MotionEvent m) {
                //Log.d("tempo", "in on touch");
                if(metronomeRunning || tempo >=240){
                    return false;
                }
                if(m.getAction()==MotionEvent.ACTION_DOWN) {
                    //Log.d("tempo", "action down");
                        //Log.d("temp", "can increase tempo");
                    if (myHandler != null)
                        return true;
                    myHandler = new Handler();
                    myHandler.postDelayed(increaseTempoAction, 0);
                }
                else{
                    //Log.d("tempo", "no longer increasing");
                    if (myHandler == null)
                        return true;
                    myHandler.removeCallbacks(increaseTempoAction);
                    myHandler = null;
                }
                return false;
            }

            Runnable increaseTempoAction = new Runnable() {
                @Override public void run() {
                    if (tempo < 240) {
                        //Log.d("tempo", "performing action increase tempo: " + tempo);
                        tempo++;
                        tvTempoVal.setText(tempo + "");
                        myHandler.postDelayed(this, 70);
                    }
                }
            };

        });

        //when the minus tempo button is touched decrease by 1 (to a min of 40)
        //if held down keep subtracting 1
        btnTempoMinus.setOnTouchListener(new View.OnTouchListener() {

            private Handler myHandler;

            @Override
            public boolean onTouch(View v, MotionEvent m) {
                //Log.d("tempo", "in on touch");
                if(metronomeRunning || tempo <=40){
                    return false;
                }
                if(m.getAction()==MotionEvent.ACTION_DOWN) {
                    if (myHandler != null)
                        return true;
                    myHandler = new Handler();
                    myHandler.postDelayed(decreaseTempoAction, 0);
                }
                else{
                    //Log.d("tempo", "no longer decreasing");
                    if (myHandler == null)
                        return true;
                    myHandler.removeCallbacks(decreaseTempoAction);
                    myHandler = null;
                }
                return false;
            }

            Runnable decreaseTempoAction = new Runnable() {
                @Override public void run() {
                    if (tempo > 40) {
                        //Log.d("tempo", "performing action decrease tempo: " + tempo);
                        tempo--;
                        tvTempoVal.setText(tempo + "");
                        myHandler.postDelayed(this, 70);
                    }
                }
            };

        });

        //when the plus beats button is touched increase by 1 (to a max of 9)
        //if held down keep adding 1
        btnBeatsPlus.setOnTouchListener(new View.OnTouchListener() {

            private Handler myHandler;

            @Override
            public boolean onTouch(View v, MotionEvent m) {
                //Log.d("tempo", "in on touch");
                if(metronomeRunning || beats >= 9){
                    return false;
                }
                if(m.getAction()==MotionEvent.ACTION_DOWN) {
                    if (myHandler != null)
                        return true;
                    myHandler = new Handler();
                    myHandler.postDelayed(increaseBeatsAction, 0);
                }
                else{
                    //Log.d("tempo", "no longer increasing");
                    if (myHandler == null)
                        return true;
                    myHandler.removeCallbacks(increaseBeatsAction);
                    myHandler = null;
                }
                return false;
            }

            Runnable increaseBeatsAction = new Runnable() {
                @Override public void run() {
                    if (beats < 9) {
                        //Log.d("tempo", "performing action increase beats: " + beats);
                        beats++;
                        tvBeatsVal.setText(beats + "");
                        myHandler.postDelayed(this, 70);
                    }
                }
            };

        });

        //when the minus beats button is touched decrease by 1 (to a min of 2)
        //if held down keep subtracting 1
        btnBeatsMinus.setOnTouchListener(new View.OnTouchListener() {

            private Handler myHandler;

            @Override
            public boolean onTouch(View v, MotionEvent m) {
                //Log.d("tempo", "in on touch");
                if(metronomeRunning || beats <=2){
                    return false;
                }
                if(m.getAction()==MotionEvent.ACTION_DOWN) {
                    if (myHandler != null)
                        return true;
                    myHandler = new Handler();
                    myHandler.postDelayed(decreaseBeatsAction, 0);
                }
                else{
                    //Log.d("tempo", "no longer increasing");
                    if (myHandler == null)
                        return true;
                    myHandler.removeCallbacks(decreaseBeatsAction);
                    myHandler = null;
                }
                return false;
            }

            Runnable decreaseBeatsAction = new Runnable() {
                @Override public void run() {
                    if (beats > 2) {
                        //Log.d("tempo", "performing action");
                        beats--;
                        tvBeatsVal.setText(beats + "");
                        myHandler.postDelayed(this, 70);
                    }
                }
            };

        });

        //when clicking the start metronome button
        //if the metronome is not running start it
        //if the metronome is running stop it
        btnStartMetronome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(!metronomeRunning){
                    btnStartMetronome.setText("Stop Metronome");
                    beatNum = 0;
                    if (myMetronomeHandler == null) {
                        myMetronomeHandler = new Handler();
                        myMetronomeHandler.postDelayed(startMetronome, 0);
                    }
                }
                else{
                    btnStartMetronome.setText("Start Metronome");
                    beatNum = 0;
                    tvBeatNumber.setText("");
                    if (myMetronomeHandler != null) {
                        myMetronomeHandler.removeCallbacks(startMetronome);
                        myMetronomeHandler = null;
                    }
                }
                metronomeRunning = !metronomeRunning;
            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null) {
            mp.release();
        }
        try {
            myMetronomeHandler.removeCallbacks(startMetronome);
        }catch(Exception e){

        }
    }

    /**
     * set the menu used
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.metronome, menu);
        return true;
    }

    /**
     * set actions for each menu option
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //return to main menu
        if (id == R.id.action_main_menu) {
            Intent newIntent = new Intent();
            newIntent.setClass(this, SongListActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newIntent);
            finish();
            return true;
        }

        //go to reminder activity
        if(id ==R.id.submenu_reminder){
            Intent newIntent = new Intent();
            newIntent.setClass(this, ReminderActivity.class);
            startActivity(newIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}