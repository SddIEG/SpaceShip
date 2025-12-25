package ru.samsung.gamestudio.objects;


import static ru.samsung.gamestudio.GameSetting.BULLET_VELOSITY;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.GameSetting;

public class BulletObject extends GameObject {
public boolean wasHit;
    public BulletObject( int x, int y, int width, int height,String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSetting.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, BULLET_VELOSITY));
        body.setBullet(true);
        wasHit = false;
    }


    public boolean hasToBeDectroid(){
        return wasHit || (getY() - height / 2 > GameSetting.SCR_HEIGHT);
    }
  @Override
    public void hit() {
        wasHit = true;
        dispose();
    }
}


