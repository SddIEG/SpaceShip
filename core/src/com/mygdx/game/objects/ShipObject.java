package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSetting;

import java.awt.Point;

public class ShipObject extends GameObject {

    public ShipObject(int x, int y, int wight, int height, String textyrePath, World world) {
        super(textyrePath, x, y, height, wight, world);

    }


    public void putInFrame() {
        if (getY() > (GameSetting.SCR_HEIGHT / 2f - height / 2f)) {
            setY(GameSetting.SCR_HEIGHT / 2 - height / 2);
        }

        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }


        if(getX()> GameSetting.SCR_WIDTH+ width/2f){
            setX(0);
        }
        if (getX() < (-width/2f)){
           setX(GameSetting.SCR_WIDTH);
        }

    }

    public void move(Vector3 vector3){

        body.applyForceToCenter(

                        new Vector2(
                                (vector3.x - getX()) * GameSetting.SHIP_FORSE_RATIO,
                                (vector3.y - getY()) * GameSetting.SHIP_FORSE_RATIO
                        ),
                        true
        );

    }


}
