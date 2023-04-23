package com.example.borrar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    private TextView timerText;
    private Button startStopButton, resetButton;
    private boolean timerRunning;
    private long timeElapsed = 0;
    private Handler handler;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);

        timerText = rootView.findViewById(R.id.timerText);
        startStopButton = rootView.findViewById(R.id.startStopButton);
        resetButton = rootView.findViewById(R.id.resetButton);

        // Set button colors
        startStopButton.setTextColor(getResources().getColor(R.color.green));
        resetButton.setTextColor(getResources().getColor(R.color.red));

        handler = new Handler();

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        resetTimer();

        return rootView;
    }

    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeElapsed += 1000;
                updateTimerText();
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        timerRunning = true;
        startStopButton.setText("STOP");
        resetButton.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        handler.removeCallbacksAndMessages(null);
        timerRunning = false;
        startStopButton.setText("START");
        resetButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeElapsed = 0;
        updateTimerText();
        resetButton.setVisibility(View.INVISIBLE);
        startStopButton.setVisibility(View.VISIBLE);
    }

    private void updateTimerText() {
        int seconds = (int) (timeElapsed / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(time);
    }
}
