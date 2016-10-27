package com.example.incresol_078.myapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Incresol-078 on 08-06-2016.
 */
public class StopWatchActivity extends AppCompatActivity {
    private TextView tempTextView; //Temporary TextView
    private Button tempBtn; //Temporary Button
    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 100;
    private String hours,minutes,seconds,milliseconds;
    private long secs,mins,hrs,msecs;
    private boolean stopped = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
    }
    public void startClick (View view){
        ((TextView) findViewById(R.id.stopTime)).setText("");
        if(stopped){
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else{
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    public void pauseClick (View view){

        mHandler.removeCallbacks(startTimer);
        stopped = true;
            ((Button)findViewById(R.id.button_start)).setText("Resume");
    }

    public void resetClick (View view){
        ((Button)findViewById(R.id.button_start)).setText("Start");
        mHandler.removeCallbacks(startTimer);
        stopped = false;
        ((TextView)findViewById(R.id.timer)).setText("00:00:00");
        ((TextView) findViewById(R.id.stopTime)).setText("");
      /*  ((TextView)findViewById(R.id.timerMs)).setText(".0");*/
    }

    public void stopClick(View view){
        mHandler.removeCallbacks(startTimer);
        stopped = false;
            ((TextView) findViewById(R.id.stopTime)).setText("Time stopped at " + hours + ":" + minutes + ":" + seconds);
        ((TextView)findViewById(R.id.timer)).setText("00:00:00");
        ((Button)findViewById(R.id.button_start)).setText("Start");

        /*else{
            ((TextView) findViewById(R.id.stopTime)).setText("");
        }*/
    }

    private void updateTimer (float time){
        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);
        msecs=(long)(time);
		/* Convert the seconds to String
		 * and format to ensure it has
		 * a leading zero when required
		 */
        secs = secs % 60;
        seconds=String.valueOf(secs);
        if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes=String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+minutes;
        }

    	/* Convert the hours to String and format the String */

        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }

		/* Setting the timer text to the elapsed time */
        ((TextView)findViewById(R.id.timer)).setText(hours + ":" + minutes + ":" + seconds);
    }

    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this,REFRESH_RATE);
        }
    };


}
