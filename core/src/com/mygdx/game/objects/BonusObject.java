package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSetting;

public class BonusObject extends GameObject {
    private boolean take = false;

    public BonusObject(int x,int y ,int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + 50 + (new Random()).nextInt((GameSetting.SCR_WIDTH - 100 - width)),
                GameSetting.SCR_HEIGHT + height / 2,
                width, height,
                GameSetting.MYSOR_BIT,
                world
        );
        body.setLinearVelocity(new Vector2(0, -5));
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }



    public boolean isTake() {
        return take;
    }

    @Override
    public void hit() {
        take = true;
    }
}
