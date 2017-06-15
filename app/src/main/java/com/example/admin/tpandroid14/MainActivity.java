package com.example.admin.tpandroid14;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isPausing = new AtomicBoolean(false);
    public static final String PROGRESS = "progress";

    ProgressBar progressBar;
    Handler handler = new Handler();
    Bundle messageBundle = new Bundle();
    Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        progressBar.setProgress(0);
        handler = new Handler(){
            public void handleMessage(Message msg){
                int p = msg.getData().getInt(PROGRESS);
                progressBar.incrementProgressBy(p);
            }
        };

        
    }

        Thread traitement = new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100 && isRunning.get(); i++) {
                        while (isPausing.get() && (isRunning.get())) {
                            Thread.sleep(1000);
                        }

                        Thread.sleep(300);

                        //recupération du message
                        message = handler.obtainMessage();
                        //message.what = PROGRESSION;
                        messageBundle.putInt(PROGRESS, i);
                        message.setData(messageBundle);
                        handler.sendMessage(message);
                    }
                } catch (Throwable t) {

                }
            }

        });


        @Override
        protected void onStart() {
            super.onStart();
            progressBar.setProgress(0);
            isRunning.set(true);
            isPausing.set(false);
            traitement.start();
        }

        @Override
        protected void onPause() {
            super.onPause();
            isPausing.set(true);
            isRunning.set(false);
        }
    }



