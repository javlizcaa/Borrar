package com.example.borrar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Fragment1 extends Fragment {

    private TextView timerText1,timerText2,timerText3,timerText4;
    private Button startStopButton1,startStopButton2,startStopButton3,startStopButton4, resetButton1, resetButton2, resetButton3, resetButton4;
    private CountDownTimer countDownTimer1, countDownTimer2, countDownTimer3, countDownTimer4;
    private boolean timer1Running, timer2Running, timer3Running, timer4Running;
    private long timeLeftInMillis1 = 600000; // 10 minutes
    private long timeLeftInMillis2 = 900000; // 15 minutes
    private long timeLeftInMillis3 = 1800000; // 30 minutes
    private long timeLeftInMillis4 = 3600000; // 1 hour
    private long countDownInterval = 1000; // 1 second

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);

        timerText1 = rootView.findViewById(R.id.timerText1);
        timerText2 = rootView.findViewById(R.id.timerText2);
        timerText3 = rootView.findViewById(R.id.timerText3);
        timerText4 = rootView.findViewById(R.id.timerText4);
        startStopButton1 = rootView.findViewById(R.id.startStopButton1);
        startStopButton2 = rootView.findViewById(R.id.startStopButton2);
        startStopButton3 = rootView.findViewById(R.id.startStopButton3);
        startStopButton4 = rootView.findViewById(R.id.startStopButton4);
        resetButton1 = rootView.findViewById(R.id.resetButton1);
        resetButton2 = rootView.findViewById(R.id.resetButton2);
        resetButton3 = rootView.findViewById(R.id.resetButton3);
        resetButton4 = rootView.findViewById(R.id.resetButton4);

        startStopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer1Running) {
                    pauseTimer1();
                } else {
                    startTimer1();
                }
            }
        });
        startStopButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer2Running) {
                    pauseTimer2();
                } else {
                    startTimer2();
                }
            }
        });
        startStopButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer3Running) {
                    pauseTimer3();
                } else {
                    startTimer3();
                }
            }
        });
        startStopButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer4Running) {
                    pauseTimer4();
                } else {
                    startTimer4();
                }
            }
        });
        resetButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer1();
            }
        });

        resetButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer2();
            }
        });

        resetButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer3();
            }
        });
        resetButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer4();
            }
        });

        updateTimerText1();
        updateTimerText2();
        updateTimerText3();
        updateTimerText4();

        resetButton1.setVisibility(View.INVISIBLE);
        resetButton2.setVisibility(View.INVISIBLE);
        resetButton3.setVisibility(View.INVISIBLE);
        resetButton4.setVisibility(View.INVISIBLE);

        return rootView;
    }

    private void startTimer1() {
        countDownTimer1 = new CountDownTimer(timeLeftInMillis1, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis1 = millisUntilFinished;
                updateTimerText1();
            }

            @Override
            public void onFinish() {
                timer1Running = false;
                startStopButton1.setText("START");
                startStopButton1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                resetButton1.setVisibility(View.VISIBLE);
            }
        }.start();

        timer1Running = true;
        startStopButton1.setText("STOP");
        startStopButton1.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        resetButton1.setVisibility(View.INVISIBLE);
    }
    private void startTimer2() {
        countDownTimer2 = new CountDownTimer(timeLeftInMillis2, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis2 = millisUntilFinished;
                updateTimerText2();
            }

            @Override
            public void onFinish() {
                timer2Running = false;
                startStopButton2.setText("START");
                startStopButton2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                resetButton2.setVisibility(View.VISIBLE);
            }
        }.start();

        timer2Running = true;
        startStopButton2.setText("STOP");
        startStopButton2.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        resetButton2.setVisibility(View.INVISIBLE);
    }
    private void startTimer3() {
        countDownTimer3 = new CountDownTimer(timeLeftInMillis3, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis3 = millisUntilFinished;
                updateTimerText3();
            }

            @Override
            public void onFinish() {
                timer3Running = false;
                startStopButton3.setText("START");
                startStopButton3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                resetButton3.setVisibility(View.VISIBLE);
            }
        }.start();

        timer3Running = true;
        startStopButton3.setText("STOP");
        startStopButton3.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        resetButton3.setVisibility(View.INVISIBLE);
    }
    private void startTimer4() {
        countDownTimer4 = new CountDownTimer(timeLeftInMillis4, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis4 = millisUntilFinished;
                updateTimerText4();
            }

            @Override
            public void onFinish() {
                timer4Running = false;
                startStopButton4.setText("START");
                startStopButton4.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                resetButton4.setVisibility(View.VISIBLE);
            }
        }.start();

        timer4Running = true;
        startStopButton4.setText("STOP");
        startStopButton4.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        resetButton4.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer1() {
        countDownTimer1.cancel();
        timer1Running = false;
        startStopButton1.setText("START");
        startStopButton1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        resetButton1.setVisibility(View.VISIBLE);
    }
    private void pauseTimer2() {
        countDownTimer2.cancel();
        timer2Running = false;
        startStopButton2.setText("START");
        startStopButton2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        resetButton2.setVisibility(View.VISIBLE);
    }
    private void pauseTimer3() {
        countDownTimer3.cancel();
        timer3Running = false;
        startStopButton3.setText("START");
        startStopButton3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        resetButton3.setVisibility(View.VISIBLE);
    }
    private void pauseTimer4() {
        countDownTimer4.cancel();
        timer4Running = false;
        startStopButton4.setText("START");
        startStopButton4.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        resetButton4.setVisibility(View.VISIBLE);
    }

    private void resetTimer1() {
        timeLeftInMillis1 = 600000;
        updateTimerText1();
        resetButton1.setVisibility(View.INVISIBLE);
        startStopButton1.setVisibility(View.VISIBLE);
    }
    private void resetTimer2() {
        timeLeftInMillis2 = 900000;
        updateTimerText2();
        resetButton2.setVisibility(View.INVISIBLE);
        startStopButton2.setVisibility(View.VISIBLE);
    }
    private void resetTimer3() {
        timeLeftInMillis3 = 1800000;
        updateTimerText3();
        resetButton3.setVisibility(View.INVISIBLE);
        startStopButton3.setVisibility(View.VISIBLE);
    }
    private void resetTimer4() {
        timeLeftInMillis4 = 3600000;
        updateTimerText4();
        resetButton4.setVisibility(View.INVISIBLE);
        startStopButton4.setVisibility(View.VISIBLE);
    }

    private void updateTimerText1() {
        int hours = (int) (timeLeftInMillis1 / (1000 * 60 * 60));
        int minutes = (int) ((timeLeftInMillis1 / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMillis1 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText1.setText(timeLeftFormatted);
    }
    private void updateTimerText2() {
        int hours = (int) (timeLeftInMillis2 / (1000 * 60 * 60));
        int minutes = (int) ((timeLeftInMillis2 / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMillis2 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText2.setText(timeLeftFormatted);
    }
    private void updateTimerText3() {
        int hours = (int) (timeLeftInMillis3 / (1000 * 60 * 60));
        int minutes = (int) ((timeLeftInMillis3 / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMillis3 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText3.setText(timeLeftFormatted);
    }
    private void updateTimerText4() {
        int hours = (int) (timeLeftInMillis4 / (1000 * 60 * 60));
        int minutes = (int) ((timeLeftInMillis4 / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMillis4 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText4.setText(timeLeftFormatted);
    }
}
