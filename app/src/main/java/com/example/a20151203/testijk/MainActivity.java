package com.example.a20151203.testijk;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;

import com.example.a20151203.testijk.media.AndroidMediaController;
import com.example.a20151203.testijk.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IjkVideoView vv = findViewById(R.id.video_view);
        vv.setMediaController(new AndroidMediaController(this));
        vv.setVideoPath("http://cdn-fms.rbs.com.br/vod/hls_sample1_manifest.m3u8");
//        vv.setVideoPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//        vv.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer mp) {
//                mp.start();
//            }
//        });
        String mp3_url = "http://mp3.haoduoge.com/s/2017-07-04/1499180571.mp3";
        SeekBar seekBar = findViewById(R.id.sb);
        final MyVoicePlayer player = new MyVoicePlayer(mp3_url, seekBar);
//        final IjkMediaPlayer player = new IjkMediaPlayer();
////        Uri uri = Uri.parse();
//        try {
//            player.setDataSource("http://mp3.haoduoge.com/s/2017-07-04/1499180571.mp3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        findViewById(R.id.btn_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                player.reset();
//                player.prepareAsync();
//                player.start();
                player.start();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = (int) (progress * player.ijkMediaPlayer.getDuration()
                                        / seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        player.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer mp) {
//                mp.start();
//            }
//        });
    }
}
