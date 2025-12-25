package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.GameSetting.SCR_HEIGHT;
import static ru.samsung.gamestudio.GameSetting.SCR_WIDTH;
import static ru.samsung.gamestudio.GameState.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

import ru.samsung.gamestudio.ButtonView;
import ru.samsung.gamestudio.ContactManager;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameSetting;
import ru.samsung.gamestudio.GameState;
import ru.samsung.gamestudio.ImgView;
import ru.samsung.gamestudio.LiveView;
import ru.samsung.gamestudio.MemoryManager;
import ru.samsung.gamestudio.MovingFonView;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.RecordsListView;
import ru.samsung.gamestudio.TextView;
import ru.samsung.gamestudio.objects.BonusObject;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.MysorObjects;
import ru.samsung.gamestudio.objects.ShipObject;

public class IecranGame extends ScreenAdapter {
    MyGdxGame mGG;
    Random r;
    long dubleShootTimer;
    ImgView topBlackoutView, fullBackground;
    ShipObject shipObject;
    ButtonView pauseBotton, homeButton, continueButton;
    GameResources gameResources;
    MovingFonView movingFonView;
    GameSession gameSession;
    ContactManager contactManager;
    LiveView liveView;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    Body body;
    ArrayList<MysorObjects> mysorArray;
    ArrayList<BulletObject> bulletArrey;
    ArrayList<BonusObject> bonusArray;
    TextView scoreTextView;

    public IecranGame(MyGdxGame mGG) {
        this.mGG = mGG;
        r = new Random();
        dubleShootTimer = 0;
        mysorArray = new ArrayList<>();
        contactManager = new ContactManager(mGG.world);
        recordsListView = new RecordsListView(mGG.commonWhiteFont, 690);
        recordsTextView = new TextView(mGG.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                mGG.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "Home");
        movingFonView = new MovingFonView(GameResources.FON_IMG_PATH, 0, 0);
        topBlackoutView = new ImgView(0, 1180, GameResources.IMG_IMG_PATH, 0, 0);
        fullBackground = new ImgView(0, 0, GameResources.IMG_RESUME_PATH, 0, 0);
        liveView = new LiveView(305, 1215, 0, 0);
        scoreTextView = new TextView(mGG.commonWhiteFont, 50, 1215);
        pauseBotton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);

        //
        homeButton = new ButtonView(160, GameSetting.SCR_HEIGHT / 2, 200, 75, mGG.commonBlackFont, GameResources.IMG_BUTTON_PATH, "Home");
        continueButton = new ButtonView(400, GameSetting.SCR_HEIGHT / 2, 200, 75, mGG.commonBlackFont, GameResources.IMG_BUTTON_PATH, "Continue");
//

        gameSession = new GameSession();
        bulletArrey = new ArrayList<>();
        bonusArray = new ArrayList<>();

        shipObject = new ShipObject(GameSetting.SCR_WIDTH / 2,
                150, GameSetting.SHIP_WIDTH,
                GameSetting.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                mGG.world);
    }


    @Override
    public void show() {
        restartGame();
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            mGG.touch = mGG.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseBotton.isHit(mGG.touch.x, mGG.touch.y)) {
                        gameSession.pauseGame();
                    }

                    shipObject.move(mGG.touch);
                    break;

                case PAUSED:
                    System.out.println(778);
                    if (continueButton.isHit(mGG.touch.x, mGG.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(mGG.touch.x, mGG.touch.y)) {
                        mGG.setScreen(mGG.menuScreen);
                    }
                    break;
                case ENDED:
                    if(homeButton2.isHit(mGG.touch.x,mGG.touch.y)){
                        mGG.setScreen(mGG.menuScreen);
                    }
                    break;

            }


        }

    }

    public void draw() {
        ScreenUtils.clear(1, 1, 0, 1);
        mGG.camera.update();
        mGG.batch.setProjectionMatrix(mGG.camera.combined);

        shipObject.putInFrame();
        mGG.batch.begin();

        movingFonView.draw(mGG.batch);


        for (MysorObjects mysor : mysorArray) mysor.draw(mGG.batch);

        shipObject.draw(mGG.batch);
        for (BulletObject bullet : bulletArrey) bullet.draw(mGG.batch);
        for ( BonusObject b : bonusArray) b.draw(mGG.batch);
        topBlackoutView.draw(mGG.batch);

        scoreTextView.draw(mGG.batch);
        liveView.draw(mGG.batch);
        pauseBotton.draw(mGG.batch);


        if (gameSession.state == GameState.PAUSED) {
            fullBackground.draw(mGG.batch);
            homeButton.draw(mGG.batch);
            continueButton.draw(mGG.batch);
        }else if(gameSession.state == GameState.ENDED){
            fullBackground.draw(mGG.batch);
            recordsTextView.draw(mGG.batch);
            recordsListView.draw(mGG.batch);
            homeButton2.draw(mGG.batch);
        }
        mGG.batch.end();
    }

    @Override
    public void render(float delta) {

        handleInput();

        if (gameSession.state == GameState.PLAYING) {


            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());

            }
            if (gameSession.shouldSpawnMysor()) {
                MysorObjects mysorObjects = new MysorObjects(
                        GameSetting.MYSOR_WIDTH, GameSetting.MYSOR_HEIGHT, GameResources.MYSOR_IMG_PATH,
                        mGG.world
                );
                mysorArray.add(mysorObjects);
                if (new java.util.Random().nextInt(100) < 5) {
                    bonusArray.add(new BonusObject(
                            new java.util.Random().nextInt(GameSetting.SCR_WIDTH),
                            GameSetting.SCR_HEIGHT + 50,
                            60, 60, GameResources.BULLET_IMG_PATH, mGG.world
                    ));
                }

                }


            movingFonView.moveFon();

            updateMysor();
            updateBullet();
            updateShip();
            updateBonus();
            liveView.setLeftLives(shipObject.getLivesLeft());
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            

            mGG.stepWorld();
        }
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


    private void updateShip() {
        shipObject.putInFrame();

        if (shipObject.needToShoot() ) {
            if (shipObject.doubleShoot && System.currentTimeMillis() < dubleShootTimer) {
                bulletArrey.add(new BulletObject(shipObject.getX() - 30, shipObject.getY() + 30,
                        GameSetting.BULLET_WIDTH, GameSetting.BULLET_HEIGHT, GameResources.BULLET_IMG_PATH, mGG.world));

                bulletArrey.add(new BulletObject(shipObject.getX() + 30, shipObject.getY() + 30,
                        GameSetting.BULLET_WIDTH, GameSetting.BULLET_HEIGHT, GameResources.BULLET_IMG_PATH, mGG.world));
            } else {

                bulletArrey.add(new BulletObject(shipObject.getX(), shipObject.getY() + 30,
                        GameSetting.BULLET_WIDTH, GameSetting.BULLET_HEIGHT, GameResources.BULLET_IMG_PATH, mGG.world));
            }


            if (mGG.audioManager.isSoundsOn) mGG.audioManager.shootSounds.play(0.1f);
        }



    }
    private void updateMysor() {
        for (int i = 0; i < mysorArray.size(); i++) {

            boolean hasToBeDestroyed = !mysorArray.get(i).isAlive() || !mysorArray.get(i).isInFrame();

            if (!mysorArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (mGG.audioManager.isSoundsOn) mGG.audioManager.explosionSounds.play(0.2f);

            }

            if (hasToBeDestroyed) {
                mGG.world.destroyBody(mysorArray.get(i).body);
                mysorArray.remove(i--);
            }
        }
    }

    private void updateBullet() {
        for (int i = 0; i < bulletArrey.size(); i++) {
            if (bulletArrey.get(i).hasToBeDectroid()) {
                mGG.world.destroyBody(bulletArrey.get(i).body);
                bulletArrey.remove(i--);
            }
        }
    }
private void updateBonus(){
    for (int i = 0; i <bonusArray.size() ; i++) {
        BonusObject b = bonusArray.get(i);
        if(b.isTake()){
            shipObject.doubleShoot = true;

            dubleShootTimer = System.currentTimeMillis() + 2000;
            shipObject.kolvoLives++;
            mGG.world.destroyBody(b.body);
            bonusArray.remove(i--);
        }else if (!b.isInFrame()){
            mGG.world.destroyBody(b.body);
            bonusArray.remove(i--);
        }

    }
}
    private void restartGame() {
shipObject.doubleShoot =false;
        for (int i = 0; i < mysorArray.size(); i++) {
            mGG.world.destroyBody(mysorArray.get(i).body);
            mysorArray.remove(i--);
        }
        mysorArray.clear();

        if (shipObject != null) {
            mGG.world.destroyBody(shipObject.body);
        }
        gameSession = new GameSession();

        shipObject = new ShipObject(
                SCR_WIDTH / 2, 150,
                GameSetting.SHIP_WIDTH, GameSetting.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                mGG.world
        );

        bulletArrey.clear();
        gameSession.startGame();
    }
}
