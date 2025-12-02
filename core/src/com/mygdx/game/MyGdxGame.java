package com.mygdx.game;

import static com.mygdx.game.GameSetting.POSITION_ITERATIONS;
import static com.mygdx.game.GameSetting.SCR_HEIGHT;
import static com.mygdx.game.GameSetting.SCR_WIDTH;
import static com.mygdx.game.GameSetting.STEP_TIME;
import static com.mygdx.game.GameSetting.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.IecranGame;

public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public IecranGame gameScreen;
    public World world;
    float accumulator = 0;
    public Vector3 touch;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0,0), true);
        world.step(STEP_TIME,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        gameScreen = new IecranGame(this);
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        setScreen(gameScreen);
    }
    public void stepWorld(){
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;
        if (accumulator>= STEP_TIME){
            accumulator -= STEP_TIME;
            world.step(STEP_TIME,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();

    }
}
