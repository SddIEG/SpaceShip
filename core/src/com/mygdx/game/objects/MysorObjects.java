package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSetting;

import java.util.Random;

public class MysorObjects extends GameObject {

    private static final int paddingHorizontal = 30;

   public MysorObjects(int width, int height, String texturePath, World world) {
       super(
               texturePath,
               width / 2 + paddingHorizontal + (new Random()).nextInt((GameSetting.SCR_WIDTH - 2 * paddingHorizontal - width)),
               GameSetting.SCR_HEIGHT + height / 2,
               width, height,
               world
       );
       body.setLinearVelocity(new Vector2(0, -GameSetting.TRASH_VELOSITY));
   }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }
}
