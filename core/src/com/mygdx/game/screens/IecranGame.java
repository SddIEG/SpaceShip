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
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSetting;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.MysorObjects;
import com.mygdx.game.objects.ShipObject;

import java.awt.Point;
import java.util.ArrayList;

import sun.swing.BakedArrayList;

public class IecranGame extends ScreenAdapter {
    MyGdxGame mGG;
    ShipObject shipObject;
    GameResources gameResources;
    GameSession gameSession;

    Body body;
    ArrayList<MysorObjects> mysorArray;
    ArrayList<BulletObject> bulletArrey;

    public IecranGame(MyGdxGame mGG) {
        this.mGG = mGG;
        mysorArray = new ArrayList<>();
        gameSession = new GameSession();
        bulletArrey = new ArrayList<>();

        shipObject = new ShipObject(GameSetting.SCR_WIDTH / 2,
                150, GameSetting.SHIP_WIDTH,
                GameSetting.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                mGG.world);
    }


    @Override
    public void show() {
        gameSession.startGame();
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            mGG.touch = mGG.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(mGG.touch);

        }

    }

    public void draw() {
        ScreenUtils.clear(1, 1, 0, 1);
        mGG.camera.update();
        mGG.batch.setProjectionMatrix(mGG.camera.combined);


        shipObject.putInFrame();
        mGG.batch.begin();
        shipObject.draw(mGG.batch);
        mGG.batch.end();
    }

    @Override
    public void render(float delta) {
        mGG.stepWorld();
        if (shipObject.needToShoot()) {
            BulletObject laserBullet = new BulletObject(
                    GameResources.BULLET_IMG_PATH , shipObject.getY() + shipObject.height / 2,
                    GameSetting.BULLET_WIDTH, GameSetting.BULLET_HEIGHT,shipObject.getX()
                  ,
                    mGG.world
            );
            bulletArrey.add(laserBullet);
        }
        if (gameSession.shouldSpawnTrash()) {
            MysorObjects mysorObjects = new MysorObjects(
                    GameSetting.TRASH_WIDTH, GameSetting.TRASH_HEIGHT, GameResources.MYSOR_IMG_PATH,
                    mGG.world
            );
            mysorArray.add(mysorObjects);
        }

        updateMysor();
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

    private void updateMysor() {
        for (int i = 0; i < mysorArray.size(); i++) {
            if (!mysorArray.get(i).isInFrame()) {
                mGG.world.destroyBody(mysorArray.get(i).body);
                mysorArray.remove(i--);
            }
        }
    }private void updateBullet() {
        for (int i = 0; i < bulletArrey.size(); i++) {
            if (!bulletArrey.get(i).hasToBeDectroid()) {
                mGG.world.destroyBody(bulletArrey.get(i).body);
                bulletArrey.remove(i--);
            }
        }
    }
}
