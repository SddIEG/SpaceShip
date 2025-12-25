package ru.samsung.gamestudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music backroundMusic;
    public Sound shootSounds;
    public Sound explosionSounds;
    public boolean isSoundsOn, isMusicOn;

   public AudioManager() {
        backroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKROUND_SOUND_PATH));
        shootSounds= Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSounds = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESCTROY_SOUND_PATH));


        backroundMusic.setVolume(0.2f);
        backroundMusic.setLooping(true);

       updateMusicFlag();
       updateSoundFlag();
    }
    public void updateSoundFlag() {
        isSoundsOn = MemoryManager.loadIsSound();
    }

    public void updateMusicFlag() {
         isMusicOn = MemoryManager.loadIsMusic();
        if (isMusicOn) backroundMusic.play();
        else backroundMusic.stop();
    }


}
