package com.tamim.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mysongs1;

    private GameView flyingFishView;
    private Handler handler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mysongs1 = MediaPlayer.create(MainActivity.this, R.raw.music1);
        mysongs1.start();



        flyingFishView = new GameView(this);

        setContentView(flyingFishView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        flyingFishView.invalidate();
                    }
                });
            }
        }, 0, Interval);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the music when the activity pauses
        if (mysongs1 != null) {
            mysongs1.stop();
            mysongs1.release();
            mysongs1 = null;
        }
    }
}