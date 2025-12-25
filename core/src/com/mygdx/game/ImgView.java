package ru.samsung.gamestudio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImgView extends View{
    Texture texture;
    int height,weight;
    public ImgView(float x, float y, String imgPath , float height,float weight) {
        super(x, y,height,weight);
        texture = new Texture(imgPath);
        this.height = texture.getHeight();
        this.weight = texture.getWidth();
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture,x,y,weight,height);
    }
    public void dispose(){
        texture.dispose();
    }
}
