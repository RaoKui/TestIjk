package com.example.a20151203.testijk;

import android.app.Activity;
import android.os.Bundle;

import media.AndroidMediaController;
import media.IjkVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IjkVideoView vv = findViewById(R.id.video_view);
        vv.setMediaController(new AndroidMediaController(this));
        vv.setVideoPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        vv.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mp.start();
            }
        });
    }
}
