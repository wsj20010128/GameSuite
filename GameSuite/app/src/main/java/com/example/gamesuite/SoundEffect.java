package com.example.gamesuite;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Sound effect collection
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class SoundEffect {
    // Sound list prepared
    private static final int[] soundLoadList
            = new int[] {
            R.raw.button_click,
            R.raw.button_2048
    };
    // Sound pool for all sound effects
    private static SoundPool soundPool = null;
    // Sound effect list
    private static int[] sounds;
    // Signal of success of loading both sound effects into the main sound pool
    private static boolean soundLoaded = false;

    /**
     * Initialize sound pool and sound map
     */
    protected static void initSound(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (soundPool != null) {
            destroySoundPool();
        }
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener((sp, sampleId, status) -> soundLoaded = true);
        sounds = new int[soundLoadList.length];
        for (int soundNum = 0; soundNum < soundLoadList.length; soundNum++) {
            sounds[soundNum] = soundPool.load(activity, soundLoadList[soundNum], soundNum);
        }
    }

    /**
     * Play sound effect of click
     */
    protected static void playSound(final int which) {
        if (soundLoaded) {
            soundPool.play(sounds[which], 1f, 1f, 0, 0, 1);
        }
    }

    protected static void destroySoundPool() {
        if (soundPool !=null){
            soundPool.autoPause();
            soundPool.release();
            soundPool = null;
        }
    }
}
