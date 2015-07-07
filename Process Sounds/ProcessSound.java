package com.myllenno.lucas.habidar.Functions;

import android.content.Context;
import android.media.MediaPlayer;

/*
 * @Author: Lucas Myllenno S M Lima.
 * @Date: 05/07/2015.
 */

public class ProcessSound {

    public MediaPlayer music;
    public static MediaPlayer soundEffect;

    // ------------------------------------ Sound Background ---------------------------------------------------- //

    public void playBackgroundMusic(Context context, String nameMusic) {
        int audio = context.getResources().getIdentifier(nameMusic, "raw", context.getPackageName());
        music = MediaPlayer.create(context, audio);
        music.setLooping(true);
        music.start();
    }

    public void stopBackgroundMusic() {
        if (music.isPlaying()) {
            music.stop();
        }
    }

    public void pauseBackgroundMusic() {
        if (music.isPlaying())
            music.pause();
    }

    public void unpauseBackgroundMusic() {
        if (!music.isPlaying())
            music.start();
    }

    public void restartBackgroundMusic() {
        music.stop();
        music.setLooping(true);
        music.start();
    }

    // ------------------------------------ Sound Effect ------------------------------------------------------ //

    public void playSoundEffect(Context context, String nameSoundEffect) {
        try {
            soundEffect = MediaPlayer.create(context, getSoundEffect(context, nameSoundEffect));
            soundEffect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSoundEffect();
                    soundEffect = null;
                }
            });
            soundEffect.start();
        } catch (Exception e) {}
    }

    public static int getSoundEffect(Context context, String nameSoundEffect) {
        int sound = context.getResources().getIdentifier(nameSoundEffect, "raw", context.getPackageName());
        return sound;
    }

    public static void stopSoundEffect() {
        if ((soundEffect != null) && (soundEffect.isPlaying())) {
            soundEffect.stop();
            soundEffect.release();
        }
    }
}