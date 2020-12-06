package com.example.todoroomapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundManager {

    private SoundPool soundPool;
    private int finishTaskSound;
    private int unfinishedTaskSound;

    public SoundManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else { //old versions
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        finishTaskSound = soundPool.load(context,R.raw.pick_gold_coin,1);
        unfinishedTaskSound = soundPool.load(context,R.raw.pick_silver_coin,1);
    }

    public void playFinishSfx() {
        soundPool.play(finishTaskSound,0.5f,0.5f,1,0,1);
    }

    public void playUndoneSfx() {
        soundPool.play(unfinishedTaskSound,0.3f,0.3f,1,0,1);
    }

    public void stopSoundPool() {
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
