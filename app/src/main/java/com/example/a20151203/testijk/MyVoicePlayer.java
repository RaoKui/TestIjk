package com.example.a20151203.testijk;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by RaoKui on 2017/7/10.
 */

public class MyVoicePlayer implements IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnPreparedListener
        , IMediaPlayer.OnCompletionListener {

    private SeekBar mSeekbar;

    public  IjkMediaPlayer ijkMediaPlayer;

    private String voice_url;

    private final static String TAG = "voice_player";

    private Timer mTimer = new Timer();

    private boolean pause;

    private int playPosition;

    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (ijkMediaPlayer == null)
                return;
            if (ijkMediaPlayer.isPlaying() && mSeekbar.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = (int) ijkMediaPlayer.getCurrentPosition();
            int duration = (int) ijkMediaPlayer.getDuration();
            if (duration > 0) {
                long pos = mSeekbar.getMax() * position / duration;
                mSeekbar.setProgress((int) pos);
            }
        }

        ;
    };

    public MyVoicePlayer(String voice_url, SeekBar seekBar) {
        mSeekbar = seekBar;
        this.voice_url = voice_url;

        ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ijkMediaPlayer.setOnPreparedListener(this);
        ijkMediaPlayer.setOnPreparedListener(this);
        ijkMediaPlayer.setOnCompletionListener(this);

        mTimer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        mSeekbar.setSecondaryProgress(percent);
        int currentProgress = (int) (mSeekbar.getMax()
                * ijkMediaPlayer.getCurrentPosition() / ijkMediaPlayer.getDuration());
        Log.e(currentProgress + "% play", percent + "% buffer");
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
        Log.d(TAG, "播放完成");
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        mp.start();
    }

    public void start() {
        playNet(0);
    }

    /**
     * 播放音乐
     *
     * @param playPosition
     */
    private void playNet(int playPosition) {
        try {
            ijkMediaPlayer.reset();// 把各项参数恢复到初始状态
            ijkMediaPlayer.setDataSource(voice_url);
            ijkMediaPlayer.prepareAsync();// 进行缓冲
            ijkMediaPlayer.setOnPreparedListener(new MyPreparedListener(
                    playPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class MyPreparedListener implements
            IMediaPlayer.OnPreparedListener {
        private int playPosition;

        public MyPreparedListener(int playPosition) {
            this.playPosition = playPosition;
        }

        @Override
        public void onPrepared(IMediaPlayer mp) {
            ijkMediaPlayer.start();// 开始播放
            if (playPosition > 0) {
                ijkMediaPlayer.seekTo(playPosition);
            }
        }
    }
}
