package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSetting;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.ShipObject;

import java.awt.Point;

public class IecranGame extends ScreenAdapter {
    MyGdxGame mGG;
    ShipObject shipObject;
    GameResources gameResources;
    Body body;

    public IecranGame(MyGdxGame mGG) {
        this.mGG = mGG;

        shipObject = new ShipObject(GameSetting.SCR_WIDTH/2,
                150, GameSetting.SHIP_WIDTH,
                GameSetting.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                mGG.world);
    }


    @Override
    public void show() {

    }
    public  void handleInput(){
        if (Gdx.input.isTouched()){
            mGG.touch = mGG.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            shipObject.move(mGG.touch);
        }

    }
public void draw(){
    mGG.camera.update();
    mGG.batch.setProjectionMatrix(mGG.camera.combined);
    ScreenUtils.clear(1, 1, 0, 1);

    mGG.batch.begin();
    shipObject.draw(mGG.batch);
    mGG.batch.end();
}
    @Override
    public void render(float delta) {
      mGG.stepWorld();
      shipObject.putInFrame();
      handleInput();
      draw();

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
