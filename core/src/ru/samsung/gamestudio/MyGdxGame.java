package ru.samsung.gamestudio;


import static ru.samsung.gamestudio.GameSetting.POSITION_ITERATIONS;
import static ru.samsung.gamestudio.GameSetting.SCR_HEIGHT;
import static ru.samsung.gamestudio.GameSetting.SCR_WIDTH;
import static ru.samsung.gamestudio.GameSetting.STEP_TIME;
import static ru.samsung.gamestudio.GameSetting.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.screens.IecranGame;
import ru.samsung.gamestudio.screens.MenuScreen;

public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
   public BitmapFont commonWhiteFont, largeWhiteFont , commonBlackFont;
    public IecranGame gameScreen;
    public World world;
    float accumulator = 0;
    public Vector3 touch;
   public MenuScreen menuScreen;
    public IecranGame iecranGame;
    public AudioManager audioManager;
    public SettingScreen settingScreen;

    @Override
    public void create() {
        Box2D.init();
        audioManager = new AudioManager();
        world = new World(new Vector2(0,0), true);
        world.step(STEP_TIME,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        commonWhiteFont = FontBuilder. generate (24, Color.WHITE, GameResources.FONTS_PATH);
        commonBlackFont = FontBuilder. generate (24, Color.BLACK, GameResources.FONTS_PATH);
        largeWhiteFont = FontBuilder. generate (48, Color.WHITE, GameResources.FONTS_PATH);

        settingScreen = new SettingScreen(this);
        iecranGame = new IecranGame(this);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        menuScreen = new MenuScreen(this);
        camera.setToOrtho(false, SCR_WIDTH ,SCR_HEIGHT);
        setScreen(menuScreen);
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
