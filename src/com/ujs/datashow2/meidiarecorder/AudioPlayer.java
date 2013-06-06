package com.ujs.datashow2.meidiarecorder;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
/**
 * 工具类
 * 音频播放
 * @author zouning
 *
 */
public class AudioPlayer implements OnCompletionListener {
    private static final String tag = "AudioPlayer";
    private MediaPlayer mediaPlayer;

    public AudioPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    public boolean play(String url) {
        try {
            Log.v(tag, "播放录音:" + url);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            return true;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public int pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int playPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            return playPosition;
        }
        return 0;
    }
    
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public boolean restartPlay(int position) {
        try {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(position);
                mediaPlayer.start();
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

    public boolean stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

}
