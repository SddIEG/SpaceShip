package ru.samsung.gamestudio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LiveView extends View{
    Texture texture;
    int livePadding = 6;
    int leftLives;
    int height, weight;
    public LiveView(float x, float y,float height,float weight) {
        super(x, y,height,weight);
        texture = new Texture(GameResources.IMG_LIVES_PATH);
        this.height = texture.getHeight();
        this.weight = texture.getWidth();
        leftLives = 0;

    }


    public void setLeftLives(int leftLives){
        this.leftLives = leftLives;
    }



    public void draw(SpriteBatch batch) {
        for (int i = 0; i <= leftLives; i++) {
            batch.draw(texture,x + y+i*(texture.getWidth()+livePadding),y,weight,height);

        }
        if (leftLives > 0) batch.draw(texture, x + (texture.getWidth() + livePadding), y, weight, height);
        if (leftLives > 1) batch.draw(texture, x, y, weight, height);
        if (leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding), y, weight, height);
    }
}
