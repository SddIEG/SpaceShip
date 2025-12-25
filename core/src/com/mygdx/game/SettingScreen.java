package ru.samsung.gamestudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class SettingScreen extends ScreenAdapter {
    MyGdxGame mGG;
    ButtonView homeButton;
    TextView titleView,musicView,soundsView,recordView;
    MovingFonView bacroundView;
    ImgView podlozkaView;
    public SettingScreen(MyGdxGame mGG){
        this.mGG = mGG;

        homeButton = new ButtonView(GameSetting.SCR_WIDTH-500,50,440,70,mGG.commonBlackFont,GameResources.BUTTON_LONG_BG_IMG_PATH,"Home");
        bacroundView = new MovingFonView(GameResources.FON_IMG_PATH,0,0);
        podlozkaView = new ImgView(0,0,GameResources.IMG_RESUME_PATH,350,300);
        titleView = new TextView(mGG.largeWhiteFont,150,GameSetting.SCR_HEIGHT/2 + 400,"Settings");
        musicView = new TextView(mGG.largeWhiteFont,GameSetting.SCR_WIDTH/2-200,GameSetting.SCR_HEIGHT/2+100,"Music" + "ON");
        soundsView = new TextView(mGG.largeWhiteFont,GameSetting.SCR_WIDTH/2-200,GameSetting.SCR_HEIGHT/2,"Sounds" + "ON");
        recordView = new TextView(mGG.largeWhiteFont,GameSetting.SCR_WIDTH/2-200,GameSetting.SCR_HEIGHT/2-100,"Record Clear");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        mGG.camera.update();
        mGG.batch.setProjectionMatrix(mGG.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        mGG.batch.begin();

        bacroundView.draw(mGG.batch);
        podlozkaView.draw(mGG.batch);
        titleView.draw(mGG.batch);
        musicView.draw(mGG.batch);
        soundsView.draw(mGG.batch);
        recordView.draw(mGG.batch);
        homeButton.draw(mGG.batch);
        handleInput();


        mGG.batch.end();



    }
    void handleInput() {
        if (Gdx.input.justTouched()) {
            mGG.touch = mGG.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (homeButton.isHit(mGG.touch.x, mGG.touch.y)) {
                mGG.setScreen(mGG.menuScreen);
            }
            if (recordView.isHit(mGG.touch.x, mGG.touch.y)) {
                recordView.setText("clear records (cleared)");
                MemoryManager.saveTableOfRecords(new ArrayList<>());
            }
            if (musicView.isHit(mGG.touch.x, mGG.touch.y)) {
                MemoryManager.saveMusicSetting(!MemoryManager.loadIsMusic());
                musicView.setText("music: " + translateStateToText(mGG.audioManager.isMusicOn));
                mGG.audioManager.updateMusicFlag();
            }
            if (soundsView.isHit(mGG.touch.x, mGG.touch.y)) {
                MemoryManager.saveSoundSetting(!MemoryManager.loadIsSound());
                mGG.audioManager.isSoundsOn = !mGG.audioManager.isSoundsOn;
                soundsView.setText("sound: " + translateStateToText(mGG.audioManager.isSoundsOn));
            }
        }



    }
    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


    }
}
