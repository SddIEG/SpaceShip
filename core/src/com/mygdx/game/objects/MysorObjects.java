package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSetting;
import ru.samsung.gamestudio.objects.GameObject;

public class MysorObjects extends GameObject {
    private int kolvoLivesMysor;

    private static final int paddingHorizontal = 30;

   public MysorObjects(int width, int height, String texturePath, World world) {
       super(
               texturePath,
               width / 2 + paddingHorizontal + (new Random()).nextInt((GameSetting.SCR_WIDTH - 2 * paddingHorizontal - width)),
               GameSetting.SCR_HEIGHT + height / 2,
               width, height,
               GameSetting.MYSOR_BIT,
               world
       );
       body.setLinearVelocity(new Vector2(0, -GameSetting.MYSOR_VELOSITY));
       kolvoLivesMysor =1;
   }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }
    public boolean isAlive() {
        return kolvoLivesMysor > 0;
    }
    @Override
    public void hit(){
       kolvoLivesMysor -=1;

    }
}
