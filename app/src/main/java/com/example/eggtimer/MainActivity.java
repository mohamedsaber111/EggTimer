package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView ;
    Button controllerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive=false;
    }
    public void updateTimer(int secondsLeft){
        // convert seconds to minutes and seconds
        int minutes = (int)secondsLeft / 60 ; //(int) to give me a whole number
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if(seconds<=9){
            secondString= "0"+secondString; // to fixed 0:9 to 0:09
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondString);
    }
  public  void controlTimer(View view) { //button
        if(counterIsActive==false) {
            counterIsActive = true; //minute 29 in video
            timerSeekBar.setEnabled(false);
            controllerButton.setText("stop");

            countDownTimer =new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) { // 25:52 in video

                @Override
                public void onTick(long millisUntilFinished) {
                    // update the value of the timer
                    updateTimer((int) millisUntilFinished / 1000); //millisecond / 1000 to get num of second
                }

                @Override
                public void onFinish() {
                    // timerTextView.setText("0:00");
                    Log.i("finished ", "timer done!");
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        }else {
            // counterIsActive is True
            resetTimer();
        }
  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controllerButton=(Button)findViewById(R.id.controllerButton);
        timerTextView =(TextView)findViewById(R.id.timerTextView);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(1200);  // 600>>10 minutes , 1200>>20minutes
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}