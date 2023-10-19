package com.tamim.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class WelcomeSkin extends AppCompatActivity {
    MediaPlayer mysongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_skin);

        mysongs = MediaPlayer.create(WelcomeSkin.this,R.raw.music);
        mysongs.start();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4200);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent mainIntent = new Intent(WelcomeSkin.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mysongs.release();
        finish();
    }
}