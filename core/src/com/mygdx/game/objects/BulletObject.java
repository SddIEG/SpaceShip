package com.mygdx.game.objects;

import static com.mygdx.game.GameSetting.BULLET_VELOSITY;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BulletObject extends GameObject {

    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world);
        body.setLinearVelocity(new Vector2(0, BULLET_VELOSITY));
    }


    public boolean hasToBeDectroid(){
        return false;
    }
}
