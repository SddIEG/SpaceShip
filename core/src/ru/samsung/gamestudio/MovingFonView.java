package ru.samsung.gamestudio;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingFonView extends View{
    Texture texture;
    int texture1Y;
    int texture2Y;
    int speed = 2;
    public MovingFonView(String pathToTexture,float height,float weight) {
        super(0, 0,height,weight);
        texture1Y = 0;
        texture2Y = GameSetting.SCR_HEIGHT;
        texture = new Texture(pathToTexture);

    }


    public void moveFon(){
        texture1Y -= speed;
        texture2Y -= speed;

        if(texture1Y<= - GameSetting.SCR_HEIGHT){
            texture1Y=GameSetting.SCR_HEIGHT;
        }if(texture2Y<= - GameSetting.SCR_HEIGHT){
            texture2Y=GameSetting.SCR_HEIGHT;
        }
    }
public void draw(SpriteBatch batch){
    batch.draw(texture, 0, texture1Y, GameSetting.SCR_WIDTH, GameSetting.SCR_HEIGHT);
    batch.draw(texture, 0, texture2Y, GameSetting.SCR_WIDTH, GameSetting.SCR_HEIGHT);





}
@Override
public void dispose(){
        texture.dispose();
}

}
