package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.ButtonView;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MovingFonView;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.TextView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame mGG;
    MovingFonView backroundViw;
    TextView titleView;
    ButtonView startView, settingsView, exitView;



    public MenuScreen(MyGdxGame mGG) {
        this.mGG = mGG;

        startView = new ButtonView(140, 646, 440, 70, mGG.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsView = new ButtonView(140, 551, 440, 70, mGG.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitView = new ButtonView(140, 456, 440, 70, mGG.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");


        backroundViw = new MovingFonView(GameResources.FON_IMG_PATH, 0, 0);

        titleView = new TextView(mGG.largeWhiteFont, 180, 960, "Space Cleaner");
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            mGG.touch = mGG.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));


            if (startView.isHit(mGG.touch.x, mGG.touch.y)) {
                mGG.setScreen(mGG.iecranGame);
            } if (settingsView.isHit(mGG.touch.x, mGG.touch.y)) {
                mGG.setScreen(mGG.settingScreen);
            } if (exitView.isHit(mGG.touch.x, mGG.touch.y)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        mGG.camera.update();
        mGG.batch.setProjectionMatrix(mGG.camera.combined);
        ScreenUtils.clear(Color.WHITE);

        mGG.batch.begin();

        backroundViw.draw(mGG.batch);
        titleView.draw(mGG.batch);
        titleView.draw(mGG.batch);
        exitView.draw(mGG.batch);
        settingsView.draw(mGG.batch);
        startView.draw(mGG.batch);

        mGG.batch.end();
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
