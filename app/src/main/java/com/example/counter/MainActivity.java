package com.example.counter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart, buttonStop;
    TextView counterValue;
    public int counter = 1;
    public boolean running = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);
        buttonStop = (Button) findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(this);
        counterValue = (TextView) findViewById(R.id.counter_value);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(buttonStart)) {
            counterStart();
        } else if (view.equals(buttonStop)) {
            counterStop();
        }
    }

    private void counterStop() {
        this.running = false;
        counter = 0;
    }

    private void counterStart() {
        running = true;
        System.out.println("Start->" + Thread.currentThread().getName());
        new MyCounter().start();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message mess) {
            counterValue.setText(String.valueOf(mess.what));
        }
    };

    class MyCounter extends Thread {
        public void run() {
            System.out.println("MyCounter->" + Thread.currentThread().getName());
            while (running) {
                counter++;
                handler.sendEmptyMessage(counter);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
        }
    }
}