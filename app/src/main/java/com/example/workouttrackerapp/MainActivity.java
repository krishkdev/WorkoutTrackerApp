package com.example.workouttrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_MILLIS = 600000;

    private TextView TextViewCountdown;
    private FloatingActionButton ButtonStart;
    private FloatingActionButton ButtonPause;
    private FloatingActionButton ButtonReset;
    private EditText workoutType;
    private TextView result;
    private CountDownTimer CountDownTimer;

    String timeLeftFormatted;

    private boolean TimerRunning;
    private long TimeLeftInMillis = START_TIME_MILLIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextViewCountdown = (TextView)findViewById(R.id.countdownText);
        ButtonStart = (FloatingActionButton)findViewById(R.id.floatingStartButton);
        ButtonPause = (FloatingActionButton)findViewById(R.id.floatingPauseButton);
        ButtonReset = (FloatingActionButton)findViewById(R.id.floatingResetButton);
        workoutType = (EditText)findViewById(R.id.workoutType);
        result = (TextView)findViewById(R.id.result);

        ButtonStart.setOnClickListener(v -> {
            if(!TimerRunning) {
                startTimer();
            }
        });
        ButtonPause.setOnClickListener(v -> {
            if(TimerRunning) {
                pauseTimer();
            }
        });
        ButtonReset.setOnClickListener(v -> resetTimer());
    }

    private void resetTimer() {
        if(workoutType.getText().toString().matches("")) {
            Toast.makeText(this, "Please enter the workout type", Toast.LENGTH_SHORT).show();
            return;
        }
        TimeLeftInMillis = START_TIME_MILLIS;
        updateCountdown();
        ButtonReset.setVisibility(View.INVISIBLE);
        ButtonStart.setVisibility(View.VISIBLE);
        ButtonPause.setVisibility(View.VISIBLE);

        result.setText("You still have "+timeLeftFormatted+" on "+workoutType.getText().toString()+" last time.");
    }

    private void pauseTimer() {
        CountDownTimer.cancel();
        TimerRunning = false;
        ButtonPause.setVisibility(View.INVISIBLE);
        ButtonStart.setVisibility(View.VISIBLE);
        ButtonReset.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        CountDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountdown();
            }

            @Override
            public void onFinish() {
                TimerRunning = false;
                ButtonPause.setVisibility(View.INVISIBLE);
                ButtonStart.setVisibility(View.INVISIBLE);
                ButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        TimerRunning = true;
        ButtonStart.setVisibility(View.INVISIBLE);
        ButtonReset.setVisibility(View.INVISIBLE);
        ButtonPause.setVisibility(View.VISIBLE);
    }

    private void updateCountdown() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;
        timeLeftFormatted = String.format(Locale.getDefault() , " % 02d:%02d", minutes, seconds);
        TextViewCountdown.setText(timeLeftFormatted);
    }
}